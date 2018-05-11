package com.pajato.gacha.model.character

enum class SuperRare(override val charName: String, override val url: String) : Character {
    // Player Characters
    TIKOS("Tikos Krymmonas", "https://vignette.wikia.nocookie.net/mechanus-archive/images/3/35/Tikos.gif"),
    VI_NAMI("Vi\'nami Argyris", "https://vignette.wikia.nocookie.net/mechanus-archive/images/6/6e/Vi%27nami.png"),
    MARA("Nomara Mozena", "https://vignette.wikia.nocookie.net/mechanus-archive/images/5/5a/Minimara.png"),
    GNOSIS("Vagrant Gnosis", "https://vignette.wikia.nocookie.net/mechanus-archive/images/9/94/Gnosis.png"),
    RAHMI("Rahmi Romazi", "https://vignette.wikia.nocookie.net/mechanus-archive/images/7/7e/Rahmi_transparent.png"),
    KID("Buckley Starling", "https://vignette.wikia.nocookie.net/mechanus-archive/images/d/d9/Thekid.png"),
    ESTRELLA("Estrella Punazo Abaroa", "https://vignette.wikia.nocookie.net/mechanus-archive/images/b/b7/Turnip.png"),
    SKYLOS("Skylos the Dog", "https://vignette.wikia.nocookie.net/mechanus-archive/images/0/07/Skylossprite.png"),

    // Enemies
    RRO_DALGO("Rro'Dalgo", "https://vignette.wikia.nocookie.net/mechanus-archive/images/8/86/Rrodalgo.gif");

    override val rarity = Rarity.SUPER_RARE
    override fun getValue(): String {
        return this.name
    }
}