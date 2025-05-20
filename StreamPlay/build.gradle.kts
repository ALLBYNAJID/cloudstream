import java.util.Properties

plugins {
    id("com.android.application") // or "com.android.library" if it's a library module
    kotlin("android")
}

val properties = Properties().apply {
    load(rootProject.file("local.properties").inputStream())
}

version = properties.getProperty("GHVERSION") ?: "1.0"

android {
    compileSdk = 33 // Adjust to your target SDK version

    defaultConfig {
        applicationId = "your.package.name" // Replace with your package name
        minSdk = 21 // Adjust to your min SDK version
        targetSdk = 33 // Adjust to your target SDK version
        versionCode = 1
        versionName = version.toString()

        // All buildConfigField from properties with safe defaults
        buildConfigField("String", "TMDB_API", "\"${properties.getProperty("TMDB_API") ?: ""}\"")
        buildConfigField("String", "CINEMATV_API", "\"${properties.getProperty("CINEMATV_API") ?: ""}\"")
        buildConfigField("String", "SFMOVIES_API", "\"${properties.getProperty("SFMOVIES_API") ?: ""}\"")
        buildConfigField("String", "ZSHOW_API", "\"${properties.getProperty("ZSHOW_API") ?: ""}\"")
        buildConfigField("String", "DUMP_API", "\"${properties.getProperty("DUMP_API") ?: ""}\"")
        buildConfigField("String", "DUMP_KEY", "\"${properties.getProperty("DUMP_KEY") ?: ""}\"")
        buildConfigField("String", "CRUNCHYROLL_BASIC_TOKEN", "\"${properties.getProperty("CRUNCHYROLL_BASIC_TOKEN") ?: ""}\"")
        buildConfigField("String", "CRUNCHYROLL_REFRESH_TOKEN", "\"${properties.getProperty("CRUNCHYROLL_REFRESH_TOKEN") ?: ""}\"")
        buildConfigField("String", "MOVIE_API", "\"${properties.getProperty("MOVIE_API") ?: ""}\"")
        buildConfigField("String", "ANICHI_API", "\"${properties.getProperty("ANICHI_API") ?: ""}\"")
        buildConfigField("String", "Whvx_API", "\"${properties.getProperty("Whvx_API") ?: ""}\"")
        buildConfigField("String", "CatflixAPI", "\"${properties.getProperty("CatflixAPI") ?: ""}\"")
        buildConfigField("String", "ConsumetAPI", "\"${properties.getProperty("ConsumetAPI") ?: ""}\"")
        buildConfigField("String", "FlixHQAPI", "\"${properties.getProperty("FlixHQAPI") ?: ""}\"")
        buildConfigField("String", "WhvxAPI", "\"${properties.getProperty("WhvxAPI") ?: ""}\"")
        buildConfigField("String", "WhvxT", "\"${properties.getProperty("WhvxT") ?: ""}\"")
        buildConfigField("String", "SharmaflixApikey", "\"${properties.getProperty("SharmaflixApikey") ?: ""}\"")
        buildConfigField("String", "SharmaflixApi", "\"${properties.getProperty("SharmaflixApi") ?: ""}\"")
        buildConfigField("String", "Theyallsayflix", "\"${properties.getProperty("Theyallsayflix") ?: ""}\"")
        buildConfigField("String", "GojoAPI", "\"${properties.getProperty("GojoAPI") ?: ""}\"")
        buildConfigField("String", "HianimeAPI", "\"${properties.getProperty("HianimeAPI") ?: ""}\"")
        buildConfigField("String", "Vidsrccc", "\"${properties.getProperty("Vidsrccc") ?: ""}\"")
        buildConfigField("String", "WASMAPI", "\"${properties.getProperty("WASMAPI") ?: ""}\"")
        buildConfigField("String", "KissKh", "\"${properties.getProperty("KissKh") ?: ""}\"")
        buildConfigField("String", "KisskhSub", "\"${properties.getProperty("KisskhSub") ?: ""}\"")
        buildConfigField("String", "SUPERSTREAM_THIRD_API", "\"${properties.getProperty("SUPERSTREAM_THIRD_API") ?: ""}\"")
        buildConfigField("String", "SUPERSTREAM_FOURTH_API", "\"${properties.getProperty("SUPERSTREAM_FOURTH_API") ?: ""}\"")
        buildConfigField("String", "SUPERSTREAM_FIRST_API", "\"${properties.getProperty("SUPERSTREAM_FIRST_API") ?: ""}\"")
        buildConfigField("String", "StreamPlayAPI", "\"${properties.getProperty("StreamPlayAPI") ?: ""}\"")
        buildConfigField("String", "PROXYAPI", "\"${properties.getProperty("PROXYAPI") ?: ""}\"")
    }

    buildFeatures {
        buildConfig = true
        viewBinding = true
    }

    // Optional - configure compileOptions and kotlinOptions if needed
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

cloudstream {
    language = "en"
    description = "#1 best extention based on MultiAPI"
    authors = listOf("Najid")
    status = 1
    tvTypes = listOf(
        "AsianDrama",
        "TvSeries",
        "Anime",
        "Movie",
        "Cartoon",
        "AnimeMovie"
    )
    iconUrl = "https://i3.wp.com/yt3.googleusercontent.com/ytc/AIdro_nCBArSmvOc6o-k2hTYpLtQMPrKqGtAw_nC20rxm70akA=s900-c-k-c0x00ffffff-no-rj?ssl=1"
    requiresResources = true
    isCrossPlatform = false
}

dependencies {
    val cloudstream by configurations
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.browser:browser:1.8.0")
    cloudstream("com.lagradost:cloudstream3:pre-release")
}
