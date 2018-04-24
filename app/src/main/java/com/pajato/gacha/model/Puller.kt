package com.pajato.gacha.model

import java.util.*

object Puller {
    private val random = Random()

    fun pull(): Character {
        val int = random.nextInt(101) + 1
        return when (int) {
            in 1..45 -> {
                val list = Character.CommonCharacters.values()
                val common: Character.CommonCharacters = list[random.nextInt(list.size)]
                Character.createCharacterAsRarity(common.charName, Rarity.COMMON, common.url)
            }
            in 46..70 -> {
                val list = Character.UncommonCharacters.values()
                val uncommon: Character.UncommonCharacters = list[random.nextInt(list.size)]
                Character.createCharacterAsRarity(uncommon.charName, Rarity.UNCOMMON, uncommon.url)
            }
            in 71..90 -> {
                val list = Character.RareCharacters.values()
                val rare: Character.RareCharacters = list[random.nextInt(list.size)]
                Character.createCharacterAsRarity(rare.charName, Rarity.RARE, rare.url)
            }
            in 91..101 -> {
                val list = Character.SuperRareCharacters.values()
                val superRare: Character.SuperRareCharacters = list[random.nextInt(list.size)]
                Character.createCharacterAsRarity(superRare.charName, Rarity.SUPER_RARE, superRare.url)
            }
            else -> {
                // this is impossible but it's fine whatever
                val list = Character.CommonCharacters.values()
                val common: Character.CommonCharacters = list[random.nextInt(list.size)]
                Character.createCharacterAsRarity(common.charName, Rarity.COMMON, common.url)
            }
        }
    }
}