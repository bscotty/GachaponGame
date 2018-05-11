package com.pajato.gacha.model.character


enum class Rare(override val charName: String, override val url: String) : Character {
    //Player Characters
    BLAKE("Blake Dagger", "https://vignette.wikia.nocookie.net/mechanus-archive/images/3/36/Blake.png"),
    SHROUD("Reticent Shroud", "https://vignette.wikia.nocookie.net/mechanus-archive/images/5/59/Reticent_Shroud.png"),
    REDD("Redd Rosethread", "https://vignette.wikia.nocookie.net/mechanus-archive/images/3/34/Reddsprite2.png"),
    LUCRETIA("Lucretia Scaeva", "https://vignette.wikia.nocookie.net/mechanus-archive/images/3/39/Lucretia.png/revision/20151126014327"),
    MERARI("Rahaavi Merari", "https://vignette.wikia.nocookie.net/mechanus-archive/images/7/7a/Merari_transp.png"),
    TARA("Tara Songflower", "https://vignette.wikia.nocookie.net/mechanus-archive/images/0/03/Tarasprite.png"),

    // Allies
    RIRA("Rira", "https://vignette.wikia.nocookie.net/mechanus-archive/images/e/e3/Riraflock.png"),
    BLAZONRY("Blazonry", "https://vignette.wikia.nocookie.net/mechanus-archive/images/e/ee/Blazonrust.png"),
    SYLVIE("Sylvie", "https://vignette.wikia.nocookie.net/mechanus-archive/images/1/18/Sylvie.png"),

    // Enemies
    RA_KYNIR("Ra'kynir", "https://vignette.wikia.nocookie.net/mechanus-archive/images/a/ac/Ra%27Kynir.gif"),
    JAD("Jadalochus", "http://vignette1.wikia.nocookie.net/mechanus-archive/images/5/54/Jadal.png"),
    FRAGMENT_2("Fragment Type-2", "https://vignette.wikia.nocookie.net/mechanus-archive/images/3/3c/Orkyara.gif"),
    FRAGMENT_3("Fragment Type-3", "https://vignette.wikia.nocookie.net/mechanus-archive/images/5/52/Frag_elf.gif"),
    FRAGMENT_4("Fragment Type-4", "https://vignette.wikia.nocookie.net/mechanus-archive/images/f/f3/Frag_gnom.gif"),

    // Voice Pieces
    CONTROL_RELUCTANCE("Control Reluctance", "https://vignette.wikia.nocookie.net/mechanus-archive/images/1/17/Reluc.gif"),
    SURGE("Surge of Fury", "https://vignette.wikia.nocookie.net/mechanus-archive/images/6/62/SurgeofFury.gif"),

    // Flockborn Leaders
    FLUX("Eternal Flux", "https://vignette.wikia.nocookie.net/mechanus-archive/images/d/db/Flux.png"),
    VOLCANO("Volcano Tr", "https://vignette.wikia.nocookie.net/mechanus-archive/images/a/aa/VolcanoTr.png"),

    // Misc
    FAORIC("Razeiya Faoric", "https://vignette.wikia.nocookie.net/mechanus-archive/images/5/58/Faoric.png"),
    IMPUNITY("Impunity", ""),
    ANGEL("Angel", "");

    override val rarity = Rarity.RARE
    override fun getValue(): String {
        return this.name
    }
}
