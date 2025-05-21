package com.najid

import com.lagradost.cloudstream3.TvType
import com.lagradost.cloudstream3.mainPageOf

class BdixDhakaFlix9Provider : BdixDhakaFlix14Provider() {
    override var mainUrl = "http://172.16.50.9"
    override var name = "DhakaFlix-Anime and Korean Series"
    override val tvSeriesKeyword: List<String> = emptyList()
    override val serverName: String = "DHAKA-FLIX-9"
    override val supportedTypes = setOf(
        TvType.Movie,
        TvType.AnimeMovie,
        TvType.TvSeries
    )
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
}
