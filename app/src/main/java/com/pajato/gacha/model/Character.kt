package com.pajato.gacha.model

sealed class Character(open val name: String, val rarity: Rarity, open var url: String) {
    data class SuperRare(override val name: String, override var url: String = "") : Character(name, Rarity.SUPER_RARE, url) {}
    data class Rare(override val name: String, override var url: String = "") : Character(name, Rarity.RARE, url)
    data class Uncommon(override val name: String, override var url: String = "") : Character(name, Rarity.UNCOMMON, url)
    data class Common(override val name: String, override var url: String = "") : Character(name, Rarity.COMMON, url)

    companion object {
        fun createCharacterAsRarity(name: String, rarity: Rarity, url: String = ""): Character {
            return when (rarity) {
                Rarity.SUPER_RARE -> SuperRare(name, url)
                Rarity.RARE -> Rare(name, url)
                Rarity.UNCOMMON -> Uncommon(name, url)
                Rarity.COMMON -> Common(name, url)
            }
        }
    }

    enum class SuperRareCharacters(val charName: String, val url: String) {
        // Player Characters
        TIKOS("Tikos Krymmonas", "https://vignette.wikia.nocookie.net/mechanus-archive/images/3/35/Tikos.gif"),
        VI_NAMI("Vi\'nami Argyris", "https://vignette.wikia.nocookie.net/mechanus-archive/images/6/6e/Vi%27nami.png"),
        MARA("Nomara Mozena", "https://vignette.wikia.nocookie.net/mechanus-archive/images/5/5a/Minimara.png"),
        GNOSIS("Vagrant Gnosis", "https://vignette.wikia.nocookie.net/mechanus-archive/images/9/94/Gnosis.png"),
        RAHMI("Rahmi Romazi", "https://vignette.wikia.nocookie.net/mechanus-archive/images/7/7e/Rahmi_transparent.png"),
        KID("Buckley Starling", "https://vignette.wikia.nocookie.net/mechanus-archive/images/d/d9/Thekid.png"),
        ESTRELLA("Estrella Punazo Abaroa", "https://vignette.wikia.nocookie.net/mechanus-archive/images/b/b7/Turnip.png"),
        SKYLOS("Skylos the Dog", "https://vignette.wikia.nocookie.net/mechanus-archive/images/0/07/Skylossprite.png")
    }

    enum class RareCharacters(val charName: String, val url: String) {
        //Player Characters
        BLAKE("Blake Dagger", "https://vignette.wikia.nocookie.net/mechanus-archive/images/3/36/Blake.png"),
        SHROUD("Reticent Shroud", "https://vignette.wikia.nocookie.net/mechanus-archive/images/5/59/Reticent_Shroud.png"),
        REDD("Redd Rosethread", "https://vignette.wikia.nocookie.net/mechanus-archive/images/3/34/Reddsprite2.png"),
        LUCRETIA("Lucretia Scaeva", "https://vignette.wikia.nocookie.net/mechanus-archive/images/3/39/Lucretia.png/revision/20151126014327"),
        MERARI("Rahaavi Merari", "https://vignette.wikia.nocookie.net/mechanus-archive/images/7/7a/Merari_transp.png"),
        TARA("Tara Songflower", ""),

        // Allies

        // Enemies
        RA_KYNIR("Ra'kynir", "https://vignette.wikia.nocookie.net/mechanus-archive/images/a/ac/Ra%27Kynir.gif"),
        JAD("Jadalochus", "http://vignette1.wikia.nocookie.net/mechanus-archive/images/5/54/Jadal.png"),
        FRAGMENT_2("Fragment Type-2", "https://vignette.wikia.nocookie.net/mechanus-archive/images/3/3c/Orkyara.gif"),
        FRAGMENT_3("Fragment Type-3", "https://vignette.wikia.nocookie.net/mechanus-archive/images/5/52/Frag_elf.gif"),
        FRAGMENT_4("Fragment Type-4", "https://vignette.wikia.nocookie.net/mechanus-archive/images/f/f3/Frag_gnom.gif"),

        // Voice Pieces
        CONTROL_RELUCTANCE("Control Reluctance", "https://vignette.wikia.nocookie.net/mechanus-archive/images/1/17/Reluc.gif"),

        // Flockborn Leaders
        FLUX("Eternal Flux", "https://vignette.wikia.nocookie.net/mechanus-archive/images/d/db/Flux.png"),
        VOLCANO("Volcano Tr", "https://vignette.wikia.nocookie.net/mechanus-archive/images/a/aa/VolcanoTr.png"),

        // Misc
        FAORIC("Razeiya Faoric", "https://vignette.wikia.nocookie.net/mechanus-archive/images/5/58/Faoric.png"),
        IMPUNITY("Impunity", ""),
        ANGEL("Angel", ""),
    }

    enum class UncommonCharacters(val charName: String, val url: String) {
        // Allies
        AMMO("Ammo Grivas", "https://vignette.wikia.nocookie.net/mechanus-archive/images/4/41/True_Het.gif"),
        UNIT_416("Unit 416", "https://vignette.wikia.nocookie.net/mechanus-archive/images/d/d4/416.gif"),
        HATCH("Hatch", "https://vignette.wikia.nocookie.net/mechanus-archive/images/1/13/Hatch.gif"),
        ARCH_HAND("Arch-Hand", "https://vignette.wikia.nocookie.net/mechanus-archive/images/6/62/ArchHand.png"),
        ROVIAL("Rovial Chorus", "https://vignette.wikia.nocookie.net/mechanus-archive/images/3/3f/Rovial.png"),
        LEGENDARY_LORD("Legendary Lord", "https://vignette.wikia.nocookie.net/mechanus-archive/images/f/f6/LegendaryLord.png"),

        // Enemies
        FLAMENCO("Flamenco", "https://vignette.wikia.nocookie.net/mechanus-archive/images/1/1a/Flamenco.gif"),
        GRAHAM_STARLING("Graham Starling", "https://vignette.wikia.nocookie.net/mechanus-archive/images/9/91/Gstar.png"),
        FRAGMENT("Fragment", "https://vignette.wikia.nocookie.net/mechanus-archive/images/a/a6/Kyara-.gif"),

        // Misc
        MOLE("Kip \"Mole\" Starling", "https://vignette.wikia.nocookie.net/mechanus-archive/images/6/63/Mole.png"),
        BARREL("Barrel", "https://vignette.wikia.nocookie.net/mechanus-archive/images/2/20/Barrelf.gif"),
    }

    enum class CommonCharacters(val charName: String, val url: String) {
        // Allies
        ARCA_AGENT("ARCA Agent", ""),
        AGING_WARFORGED("Aging Warforged", ""),
        MACHINE_SPIRIT_CLERIC("Machine Spirit Cleric", ""),
        DESCENT_SCAVENGER("Descent Scavenger", ""),

        // Enemies
        PATRICIDE_GOON("Patricide Goon", ""),
        DAD_BOY_GOON("Dad's Boys Goon", ""),
        HAVOC("HAVOC Construct", ""),
        HAVOC_ELITE("HAVOC Elite", ""),
        SMILING_CHILD("Smiling Child", ""),
        CLADES_AGENT("Clades Diabolus Operative", ""),
    }
}