package com.pajato.gacha.model

import com.pajato.gacha.model.character.*
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
                val list = Common.values()
                list[random.nextInt(list.size)]
            }
            in 46..70 -> {
                val list = Uncommon.values()
                list[random.nextInt(list.size)]
            }
            in 71..90 -> {
                val list = Rare.values()
                list[random.nextInt(list.size)]
            }
            in 91..100 -> {
                val list = SuperRare.values()
                list[random.nextInt(list.size)]
            }
            else -> {
                // On the off chance something goes wrong or the number fed in is wrong.
                val list = Common.values()
                list[random.nextInt(list.size)]
            }
        }
    }
}