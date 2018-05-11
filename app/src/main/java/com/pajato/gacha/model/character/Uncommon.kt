package com.pajato.gacha.model.character

enum class Uncommon(override val charName: String, override val url: String) : Character {
    // Allies
    AMMO("Ammo Grivas", "https://vignette.wikia.nocookie.net/mechanus-archive/images/4/41/True_Het.gif"),
    UNIT_416("Unit 416", "https://vignette.wikia.nocookie.net/mechanus-archive/images/d/d4/416.gif"),
    HATCH("Hatch", "https://vignette.wikia.nocookie.net/mechanus-archive/images/1/13/Hatch.gif"),
    ARCH_HAND("Arch-Hand", "https://vignette.wikia.nocookie.net/mechanus-archive/images/6/62/ArchHand.png"),
    ROVIAL("Rovial Chorus", "https://vignette.wikia.nocookie.net/mechanus-archive/images/3/3f/Rovial.png"),
    LEGENDARY_LORD("Legendary Lord", "https://vignette.wikia.nocookie.net/mechanus-archive/images/f/f6/LegendaryLord.png"),
    VAKI("Vaki Mafapedia", "https://vignette.wikia.nocookie.net/mechanus-archive/images/2/27/Vaki.png"),
    KURU("Kuru Tem", "https://vignette.wikia.nocookie.net/mechanus-archive/images/d/d8/Kuru_Tem.png"),
    ARATAK("Aratak Sudorn", "https://vignette.wikia.nocookie.net/mechanus-archive/images/e/ec/Aratak.png"),

    // Enemies
    FLAMENCO("Flamenco", "https://vignette.wikia.nocookie.net/mechanus-archive/images/1/1a/Flamenco.gif"),
    GRAHAM_STARLING("Graham Starling", "https://vignette.wikia.nocookie.net/mechanus-archive/images/9/91/Gstar.png"),
    FRAGMENT("Fragment", "https://vignette.wikia.nocookie.net/mechanus-archive/images/a/a6/Kyara-.gif"),

    // Misc
    MOLE("Kip \"Mole\" Starling", "https://vignette.wikia.nocookie.net/mechanus-archive/images/6/63/Mole.png"),
    BARREL("Barrel", "https://vignette.wikia.nocookie.net/mechanus-archive/images/2/20/Barrelf.gif"),
    JIG("Jig", "https://vignette.wikia.nocookie.net/mechanus-archive/images/c/c4/JIG.png"),
    AILEEN_GRAY("Aileen Gray", "");

    override val rarity = Rarity.UNCOMMON
    override fun getValue(): String {
        return this.name
    }
}