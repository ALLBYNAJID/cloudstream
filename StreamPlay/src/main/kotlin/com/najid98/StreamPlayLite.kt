package com.nemo

import com.nemo.StreamPlayExtractor.invoke2embed
import com.nemo.StreamPlayExtractor.invokeAllMovieland
import com.nemo.StreamPlayExtractor.invokeAnimes
import com.nemo.StreamPlayExtractor.invokeAoneroom
import com.nemo.StreamPlayExtractor.invokeDramaday
import com.nemo.StreamPlayExtractor.invokeDreamfilm
import com.nemo.StreamPlayExtractor.invokeFlixon
import com.nemo.StreamPlayExtractor.invokeKisskh
import com.nemo.StreamPlayExtractor.invokeLing
import com.nemo.StreamPlayExtractor.invokeNinetv
import com.nemo.StreamPlayExtractor.invokeNowTv
import com.nemo.StreamPlayExtractor.invokeRidomovies
import com.nemo.StreamPlayExtractor.invokeEmovies
import com.nemo.StreamPlayExtractor.invokeShowflix
import com.nemo.StreamPlayExtractor.invokeWatchCartoon
import com.nemo.StreamPlayExtractor.invokeWatchsomuch
import com.nemo.StreamPlayExtractor.invokeZoechip
import com.nemo.StreamPlayExtractor.invokeZshow
import com.nemo.StreamPlayExtractor.invokeFlixAPIHQ
import com.nemo.StreamPlayExtractor.invokeNepu
import com.nemo.StreamPlayExtractor.invokePlayer4U
import com.nemo.StreamPlayExtractor.invokeRiveStream
import com.nemo.StreamPlayExtractor.invokeStreamPlay
import com.nemo.StreamPlayExtractor.invokeSubtitleAPI
import com.nemo.StreamPlayExtractor.invokeSuperstream
import com.nemo.StreamPlayExtractor.invokeVidSrcXyz
import com.nemo.StreamPlayExtractor.invokeVidsrccc
import com.nemo.StreamPlayExtractor.invokeVidsrcsu
import com.nemo.StreamPlayExtractor.invokeWyZIESUBAPI
import com.nemo.StreamPlayExtractor.sharedPref
import com.lagradost.cloudstream3.SubtitleFile
import com.lagradost.cloudstream3.argamap
import com.lagradost.cloudstream3.runAllAsync
import com.lagradost.cloudstream3.utils.AppUtils
import com.lagradost.cloudstream3.utils.ExtractorLink
import com.nemo.StreamPlayExtractor.invokeXPrimeAPI
import com.nemo.StreamPlayExtractor.invokevidzeeMulti
import com.nemo.StreamPlayExtractor.invokevidzeeUltra

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