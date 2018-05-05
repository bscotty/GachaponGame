package com.pajato.gacha.model

import com.pajato.gacha.model.event.PullEvent
import com.pajato.gacha.model.event.RxBus
import java.util.*

object Puller {
    private val random = Random()

    fun pull(): Character {
        val int = random.nextInt(101) + 1
        val c = getRandomCharacterFromInt(int)
        RxBus.send(PullEvent(c))
        return c
    }

    fun getRandomCharacterFromInt(int: Int): Character {
        return when (int) {
            in 1..45 -> {
                val list = Character.CommonCharacters.values()
                val common: Character.CommonCharacters = list[random.nextInt(list.size)]
                common.toCharacter()
            }
            in 46..70 -> {
                val list = Character.UncommonCharacters.values()
                val uncommon: Character.UncommonCharacters = list[random.nextInt(list.size)]
                uncommon.toCharacter()
            }
            in 71..90 -> {
                val list = Character.RareCharacters.values()
                val rare: Character.RareCharacters = list[random.nextInt(list.size)]
                rare.toCharacter()
            }
            in 91..100 -> {
                val list = Character.SuperRareCharacters.values()
                val superRare: Character.SuperRareCharacters = list[random.nextInt(list.size)]
                superRare.toCharacter()
            }
            else -> {
                // this is impossible but it's fine whatever
                val list = Character.CommonCharacters.values()
                val common: Character.CommonCharacters = list[random.nextInt(list.size)]
                common.toCharacter()
            }
        }
    }
}