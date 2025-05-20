package com.najid

import com.najid.StreamPlayExtractor.invoke2embed
import com.najid.StreamPlayExtractor.invokeAllMovieland
import com.najid.StreamPlayExtractor.invokeAnimes
import com.najid.StreamPlayExtractor.invokeAoneroom
import com.najid.StreamPlayExtractor.invokeDramaday
import com.najid.StreamPlayExtractor.invokeDreamfilm
import com.najid.StreamPlayExtractor.invokeFlixon
import com.najid.StreamPlayExtractor.invokeKisskh
import com.najid.StreamPlayExtractor.invokeLing
import com.najid.StreamPlayExtractor.invokeNinetv
import com.najid.StreamPlayExtractor.invokeNowTv
import com.najid.StreamPlayExtractor.invokeRidomovies
import com.najid.StreamPlayExtractor.invokeEmovies
import com.najid.StreamPlayExtractor.invokeShowflix
import com.najid.StreamPlayExtractor.invokeWatchCartoon
import com.najid.StreamPlayExtractor.invokeWatchsomuch
import com.najid.StreamPlayExtractor.invokeZoechip
import com.najid.StreamPlayExtractor.invokeZshow
import com.najid.StreamPlayExtractor.invokeFlixAPIHQ
import com.najid.StreamPlayExtractor.invokeNepu
import com.najid.StreamPlayExtractor.invokePlayer4U
import com.najid.StreamPlayExtractor.invokeRiveStream
import com.najid.StreamPlayExtractor.invokeStreamPlay
import com.najid.StreamPlayExtractor.invokeSubtitleAPI
import com.najid.StreamPlayExtractor.invokeSuperstream
import com.najid.StreamPlayExtractor.invokeVidSrcXyz
import com.najid.StreamPlayExtractor.invokeVidsrccc
import com.najid.StreamPlayExtractor.invokeVidsrcsu
import com.najid.StreamPlayExtractor.invokeWyZIESUBAPI
import com.najid.StreamPlayExtractor.sharedPref
import com.lagradost.cloudstream3.SubtitleFile
import com.lagradost.cloudstream3.argamap
import com.lagradost.cloudstream3.runAllAsync
import com.lagradost.cloudstream3.utils.AppUtils
import com.lagradost.cloudstream3.utils.ExtractorLink
import com.najid.StreamPlayExtractor.invokeXPrimeAPI
import com.najid.StreamPlayExtractor.invokevidzeeMulti
import com.najid.StreamPlayExtractor.invokevidzeeUltra

class StreamPlayLite() : StreamPlay(sharedPref) {
    override var name = "najidPlay-Lite"

    override suspend fun loadLinks(
        data: String,
        isCasting: Boolean,
        subtitleCallback: (SubtitleFile) -> Unit,
        callback: (ExtractorLink) -> Unit
    ): Boolean {
        val token = sharedPref?.getString("token", null)
        val res = AppUtils.parseJson<LinkData>(data)
        runAllAsync(
            {
                if (!res.isAnime) invokeVidsrcsu(
                    res.id,
                    res.season,
                    res.episode,
                    callback
                )
            },
            {
                if (!res.isAnime) invokeWatchsomuch(
                    res.imdbId,
                    res.season,
                    res.episode,
                    subtitleCallback
                )
            },
            {
                if (!res.isAnime) invokeNinetv(
                    res.id,
                    res.season,
                    res.episode,
                    subtitleCallback,
                    callback
                )
            },
            {
                if (!res.isAnime && res.isCartoon) invokeWatchCartoon(
                    res.title,
                    res.year,
                    res.season,
                    res.episode,
                    subtitleCallback,
                    callback
                )
            },
            {
                if (res.isAnime) invokeAnimes(
                    res.title,
                    res.jpTitle,
                    res.epsTitle,
                    res.date,
                    res.year,
                    res.airedDate,
                    res.season,
                    res.episode,
                    subtitleCallback,
                    callback
                )
            },
            {
                if (!res.isAnime) invokeDreamfilm(
                    res.title,
                    res.season,
                    res.episode,
                    subtitleCallback,
                    callback
                )
            },
            {
                if (res.isAsian) invokeKisskh(
                    res.title,
                    res.season,
                    res.episode,
                    res.lastSeason,
                    subtitleCallback,
                    callback
                )
            },
            {
                if (!res.isAnime) invokeLing(
                    res.title, res.airedYear
                        ?: res.year, res.season, res.episode, subtitleCallback, callback
                )
            },
            {
                if (!res.isAnime) invokeFlixon(
                    res.id,
                    res.imdbId,
                    res.season,
                    res.episode,
                    callback
                )
            },
            {
                if (!res.isAnime) invokeAoneroom(
                    res.title, res.airedYear
                        ?: res.year, res.season, res.episode, subtitleCallback, callback
                )
            },
            {
                if (!res.isAnime) invokeRidomovies(
                    res.id,
                    res.imdbId,
                    res.season,
                    res.episode,
                    subtitleCallback,
                    callback
                )
            },
            {
                if (!res.isAnime) invokeEmovies(
                    res.title,
                    res.year,
                    res.season,
                    res.episode,
                    subtitleCallback,
                    callback
                )
            },
            {
                if (!res.isAnime) invokeAllMovieland(res.imdbId, res.season, res.episode, callback)
            },
            {
                if (res.isAsian) invokeDramaday(
                    res.title,
                    res.year,
                    res.season,
                    res.episode,
                    subtitleCallback,
                    callback
                )
            },
            {
                if (!res.isAnime) invoke2embed(
                    res.imdbId,
                    res.season,
                    res.episode,
                    subtitleCallback,
                    callback
                )
            },
            {
                if (!res.isAsian && !res.isBollywood &&!res.isAnime) invokeZshow(
                    res.title,
                    res.year,
                    res.season,
                    res.episode,
                    subtitleCallback,
                    callback
                )
            },
            {
                if (!res.isAnime) invokeShowflix(
                    res.title,
                    res.year,
                    res.season,
                    res.episode,
                    subtitleCallback,
                    callback
                )
            },
            {
                if (!res.isAnime) invokeZoechip(
                    res.title,
                    res.year,
                    res.season,
                    res.episode,
                    callback
                )
            },
            {
                if (!res.isAnime) invokeNepu(
                    res.title,
                    res.airedYear ?: res.year,
                    res.season,
                    res.episode,
                    callback
                )
            },
            {
                if (!res.isAnime) invokeFlixAPIHQ(
                    res.title,
                    res.season,
                    res.episode,
                    subtitleCallback,
                    callback
                )
            },
            {
                if (!res.isAnime) invokeVidsrccc(
                    res.id,
                    res.season,
                    res.episode,
                    callback
                )
            },
            {
                invokeRiveStream(
                    res.id,
                    res.season,
                    res.episode,
                    callback
                )

            },
            {
                invokeSuperstream(
                    token,
                    res.imdbId,
                    res.season,
                    res.episode,
                    callback
                )
            },
            {
                if (!res.isAnime) invokePlayer4U(
                    res.title,
                    res.season,
                    res.episode,
                    res.year,
                    callback
                )
            },
            {
                invokeStreamPlay(
                    res.id,
                    res.season,
                    res.episode,
                    subtitleCallback,
                    callback
                )
            },
            {
                if (!res.isAnime) invokeVidSrcXyz(
                    res.imdbId,
                    res.season,
                    res.episode,
                    callback
                )
            },

            //Subtitles Invokes
            {
                invokeSubtitleAPI(
                    res.imdbId,
                    res.season,
                    res.episode,
                    subtitleCallback,
                    callback
                )
            },
            {
                invokeWyZIESUBAPI(
                    res.imdbId,
                    res.season,
                    res.episode,
                    subtitleCallback,
                )
            },
            {
                if (!res.isAnime) invokeXPrimeAPI(
                    res.id,
                    res.season,
                    res.episode,
                    subtitleCallback,
                    callback
                )
            },
            {
            if (!res.isAnime) invokevidzeeUltra(
                res.id,
                res.season,
                res.episode,
                callback
            )
            },
            {
                if (!res.isAnime) invokevidzeeMulti(
                    res.id,
                    res.season,
                    res.episode,
                    callback
                )
            },

            )
        return true
    }

}
