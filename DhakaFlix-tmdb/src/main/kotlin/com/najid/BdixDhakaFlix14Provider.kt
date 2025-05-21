package com.najid

import com.lagradost.cloudstream3.*
import com.lagradost.cloudstream3.utils.*
import kotlinx.coroutines.*
import org.jsoup.nodes.Element
import java.util.Collections

open class BdixDhakaFlix14Provider : MainAPI() {
    override var mainUrl = "http://172.16.50.14"
    override var name = "DhakaFlix-Movies and Series"
    override val hasMainPage = true
    override val hasDownloadSupport = true
    override val hasQuickSearch = false
    override val instantLinkLoading = true
    override var lang = "bn"
    override val supportedTypes = setOf(TvType.Movie, TvType.AnimeMovie, TvType.TvSeries)

    // Keywords for identifying TV series content
    override val tvSeriesKeyword = listOf("Awards", "WWE", "KOREAN", "Documentary", "Anime", "TV-WEB-Series")

    private val serverName = "DHAKA-FLIX-14"
    private val year = 2025
    private val itemsPerPage = 12
    private val batchSize = 4

    init {
        getProviderCache(name).clearCache()
    }

    protected fun finalize() {
        getProviderCache(name).clearCache()
    }

    companion object {
        private val caches = mutableMapOf<String, ProviderCache>()
        private const val POSTER_CACHE_DURATION = 2 * 60 * 60 * 1000L // 2 hours
        private const val SEARCH_CACHE_DURATION = 1 * 60 * 60 * 1000L // 1 hour
        private const val TMDB_CACHE_DURATION = 30 * 60 * 1000L // 30 minutes

        @Synchronized
        fun getProviderCache(providerName: String): ProviderCache {
            return caches.getOrPut(providerName) { ProviderCache() }
        }
    }

    private class ProviderCache {
        private val posterCache = Collections.synchronizedMap(mutableMapOf<String, Pair<String?, Long>>())
        private val searchCache = Collections.synchronizedMap(mutableMapOf<String, Pair<TmdbSearchResult?, Long>>())

        fun getPosterCache() = posterCache
        fun getSearchCache() = searchCache

        fun <T> getFromCache(cache: MutableMap<String, Pair<T?, Long>>, key: String, duration: Long): T? {
            val (value, timestamp) = cache[key] ?: return null
            return if (System.currentTimeMillis() - timestamp < duration) value else null
        }

        fun <T> addToCache(cache: MutableMap<String, Pair<T?, Long>>, key: String, value: T?) {
            synchronized(cache) {
                if (cache.size > 100) {
                    val now = System.currentTimeMillis()
                    cache.entries.removeIf { (now - it.value.second) > TMDB_CACHE_DURATION }
                    if (cache.size > 50) {
                        val sorted = cache.entries.sortedBy { it.value.second }
                        sorted.take(sorted.size - 50).forEach { cache.remove(it.key) }
                    }
                }
                cache[key] = Pair(value, System.currentTimeMillis())
            }
        }

        fun clearCache() {
            synchronized(this) {
                posterCache.clear()
                searchCache.clear()
            }
        }
    }

    override val mainPage = mainPageOf(
        "English Movies/($year)/" to "English Movies",
        "English Movies (1080p)/($year) 1080p/" to "English Movies (1080p)",
        "Hindi Movies/($year)/" to "Hindi Movies",
        "IMDb Top-250 Movies/" to "IMDb Top-250 Movies",
        "Foreign Language Movies/Bangla Dubbing Movies/" to "Bangla Dubbing Movies",
        "Foreign Language Movies/Pakistani Movie/" to "Pakistani Movies",
        "Kolkata Bangla Movies/(2022)/" to "Kolkata Bangla Movies",
        "SOUTH INDIAN MOVIES/Hindi Dubbed/($year)/" to "Hindi Dubbed",
        "SOUTH INDIAN MOVIES/South Movies/$year/" to "South Movies",
        "Foreign Language Movies/Chinese Language/" to "Chinese Movies",
        "Foreign Language Movies/Japanese Language/" to "Japanese Movies",
        "Foreign Language Movies/Korean Language/" to "Korean Movies",
        "/KOREAN TV %26 WEB Series/" to "Korean TV & WEB Series",
        "TV-WEB-Series/TV Series ★%20 0%20 —%20 9/" to "TV Series ★ 0 — 9",
        "TV-WEB-Series/TV Series ♥%20 A%20 —%20 L/" to "TV Series ♥ A — L",
        "TV-WEB-Series/TV Series ♦%20 M%20 —%20 R/" to "TV Series ♦ M — R",
        "TV-WEB-Series/TV Series ♦%20 S%20 —%20 Z/" to "TV Series ♦ S — Z",
        "Animation Movies (1080p)/" to "Animation Movies",
        "Anime %26 Cartoon TV Series/Anime-TV Series ♥%20 A%20 —%20 F/" to "Anime TV Series",
        "Documentary/" to "Documentary",
        "Awards %26 TV Shows/%23 TV SPECIAL %26 SHOWS/" to "TV SPECIAL & SHOWS",
        "Awards %26 TV Shows/%23 AWARDS/" to "Awards",
        "WWE %26 AEW Wrestling/WWE Wrestling/%282025%29%20PPV/" to "WWE PPV",
        "WWE %26 AEW Wrestling/WWE Wrestling/" to "WWE"
    )

    private fun cleanNameForSearch(name: String): String {
        return name.replace(Regex("\\d{3,4}p.*"), "")
            .replace(Regex("\\.(mkv|mp4|avi|mov)"), "")
            .replace(Regex("\.*?\"), "")
            .replace(Regex("\\s*\[^)]*TV Series[^)]*\"), "")
            .replace(Regex("\\s*\[^)]*\"), "")
            .replace(Regex("\\s+"), " ")
            .trim()
    }

    private suspend fun lazyLoadTmdbData(name: String, isMovie: Boolean, loadDetails: Boolean = false): TmdbSearchResult? = coroutineScope {
        val cleanName = cleanNameForSearch(name)
        val cacheKey = "$cleanName:$isMovie"
        val cache = getProviderCache(this@BdixDhakaFlix14Provider.name)
        cache.getFromCache(cache.getSearchCache(), cacheKey, SEARCH_CACHE_DURATION)?.let { return@coroutineScope it }
        if (!loadDetails) {
            launch {
                TmdbHelper.searchTmdb(cleanName, isMovie)?.let {
                    cache.addToCache(cache.getSearchCache(), cacheKey, it)
                }
            }
            return@coroutineScope null
        }
        val result = TmdbHelper.searchTmdb(cleanName, isMovie)
        cache.addToCache(cache.getSearchCache(), cacheKey, result)
        return@coroutineScope result
    }

    override suspend fun getMainPage(page: Int, request: MainPageRequest): HomePageResponse = coroutineScope {
        val safePage = maxOf(page, 1)
        val url = "$mainUrl/$serverName/${request.data}"
        val doc = app.get(url).document

        val rows = doc.select("tbody > tr")
        val totalItems = rows.size

        val startIndex = (safePage - 1) * itemsPerPage + 2
        val endIndex = (startIndex + itemsPerPage).coerceAtMost(totalItems)

        val selectedRows = if (startIndex < endIndex) {
            rows.subList(startIndex, endIndex)
        } else emptyList()

        val results = selectedRows.chunked(batchSize).flatMap { chunk ->
            chunk.map { post ->
                async {
                    getPostResult(post, loadTmdbData = false)
                }
            }.awaitAll()
        }

        val hasNextPage = totalItems > endIndex
        newHomePageResponse(request.name, results, hasNextPage)
    }

    private suspend fun getPostResult(post: Element, loadTmdbData: Boolean = false): SearchResponse {
        val folderLink = post.selectFirst("td.fb-n > a") ?: return newAnimeSearchResponse("", "", TvType.Movie) {}
        val rawName = folderLink.text()
        val cleanName = cleanNameForSearch(rawName)
        val url = mainUrl + folderLink.attr("href")
        val isMovie = !tvSeriesKeyword.any { url.contains(it, ignoreCase = true) }

        val tmdbData = if (loadTmdbData) lazyLoadTmdbData(cleanName, isMovie) else null
        val posterUrl = findPosterUrl(url)

        return newAnimeSearchResponse(cleanName, url, TvType.Movie) {
            addDubStatus(dubExist = "Dual" in rawName, subExist = false)
            if (!posterUrl.isNullOrEmpty()) posterUrl?.let { this.posterUrl = it }
        }
    }

    override suspend fun search(query: String): List<SearchResponse> {
        return DhakaFlixUtils.doSearch(
            query = query,
            mainUrl = mainUrl,
            serverName = serverName,
            api = this
            override suspend fun load(url: String): LoadResponse = coroutineScope {
    val doc = app.get(url).document

    // Extract the raw title
    val rawTitle = doc.selectFirst("h1.title")?.text()?.trim() ?: "Unknown Title"
    val cleanTitle = cleanNameForSearch(rawTitle)

    // Check if this is a TV Series by presence of episodes or seasons
    val episodesElements = doc.select("div.episode-list a")
    val isTvSeries = episodesElements.isNotEmpty()

    if (isTvSeries) {
        // For TV Series
        val episodes = episodesElements.mapIndexed { index, element ->
            val episodeTitle = element.text().trim()
            val episodeUrl = mainUrl + element.attr("href")
            newEpisode(
                name = episodeTitle,
                url = episodeUrl,
                episode = index + 1,
                season = 1 // You can parse season if available
            )
        }

        newTvSeriesLoadResponse(cleanTitle, url, episodes)
    } else {
        // For Movies
        // Find video links or download links
        val videoLinks = mutableListOf<ExtractorLink>()

        // Example: select all video source links from the page
        doc.select("a.download-link").forEach { element ->
            val linkUrl = element.attr("href")
            val quality = element.text().substringBefore("p").trim() // Extract quality from text, e.g., "720p"
            videoLinks.add(
                newExtractorLink(
                    name = "Download $quality",
                    url = linkUrl,
                    extractor = ExtractorLinkType.Direct,
                    isM3u8 = linkUrl.endsWith(".m3u8"),
                    quality = quality
                )
            )
        }

        // Or, you might want to extract embedded player sources
        // e.g., iframe or video tags, or custom JS player URLs here

        newMovieLoadResponse(cleanTitle, url, videoLinks)
    }
            }
