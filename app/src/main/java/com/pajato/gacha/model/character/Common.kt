package com.pajato.gacha.model.character

enum class Common(override val charName: String, override val url: String) : Character {
    // Allies
    ARCA_AGENT("ARCA Agent", ""),
    ARCA_SCHOLAR("ARCA Scholar", ""),
    AGING_WARFORGED("Aging Warforged", ""),
    MACHINE_SPIRIT_CLERIC("Machine Spirit Cleric", ""),
    DESCENT_SCAVENGER("Descent Scavenger", ""),
    GOLIATH_HERO("Goliath Hero", ""),
    RECON_BIRDRIDER("Recon Birdrider", ""),
    VOLUNTEER_WARRIOR("Volunteer Warrior", ""),
    VOLUNTEER_MAGE("Volunteer Mage", ""),
    VOLUNTEER_CLERIC("Volunteer Cleric", ""),
    VOLUNTEER_ROGUE("Volunteer Rogue", ""),

    // Enemies
    PATRICIDE_GOON("Patricide Goon", ""),
    DAD_BOY("Dad's Boy", ""),
    HAVOC("HAVOC Construct", ""),
    HAVOC_ELITE("HAVOC Elite", ""),
    SMILING_CHILD("Smiling Child", ""),
    CLADES_AGENT("Clades Diabolus Operative", ""),
    GOLIATH_VILLAIN("Goliath Villain", ""),
    HELLSPAWN_MARAUDER("Hellspawn Marauder", ""),

    // Misc
    AOSITH("Carrier Aosith", ""),
    DURAHAN("Carrier Durahan", ""),
    GROZEN_POLITICIAN("Grozen Politician", ""),
    GNOMISH_TINKER("Gnomish Tinker", ""),
    VANIAN_MERCHANT("Vanian Merchant", ""),
    CELERY_VENDOR("Celery Vendor", ""),
    WHITEFIRE_CRAFTER("Whitefire Crafter", ""),
    WHITEFIRE_MERCHANT("Whitefire Merchant", ""),
    BRONN_FARMER("Bronn Farmer", ""),
    VANIAN_VALIANT("Vanian Valiant", ""),
    FEY_WANDERER("Fey Wanderer", "");

    override val rarity = Rarity.COMMON
    override fun getValue(): String {
        return this.name
    }
}