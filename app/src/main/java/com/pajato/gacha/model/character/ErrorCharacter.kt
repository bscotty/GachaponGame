package com.pajato.gacha.model.character

object ErrorCharacter : Character {
    const val ERROR_CHARACTER = "ERROR_CHARACTER"
    override val charName = "I shouldn\'t be here..."
    override val rarity: Rarity = Rarity.SUPER_RARE
    override val url: String = ""
    override fun getValue(): String {
        return ERROR_CHARACTER
    }
}