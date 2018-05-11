package com.pajato.gacha.model.event

import com.pajato.gacha.model.character.Character

class PullEvent(private val character: Character) : Event {
    override fun getData(): Character {
        return character
    }
}