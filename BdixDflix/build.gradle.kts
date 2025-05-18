// File: BdixDflix/build.gradle.kts

version = 8

android {
    namespace = "com.najid"
}

cloudstream {
    description = "Discovery BDIX Provider"
    authors = listOf("Najid")
    status = 1
    tvTypes = listOf(
        "Movie",
        "TvSeries",
        "Anime",
        "AnimeMovie",
        "OVA",
        "Cartoon",
        "AsianDrama",
        "Others",
        "Documentary"
    )
    language = "bn"
    iconUrl = "https://dflix.discoveryftp.net/assets/images/icon.png"
}

dependencies {
    // No cloudstream dependency here, root applies it already
}
