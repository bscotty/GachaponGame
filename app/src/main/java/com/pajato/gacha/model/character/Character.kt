package com.pajato.gacha.model.character

import android.util.Log

interface Character {
    val charName: String
    val rarity: Rarity
    val url: String
    fun getValue(): String

    companion object {
        fun getCharacter(name: String): Character {
            return try {
                Common.valueOf(name)
            } catch (e: IllegalArgumentException) {
                try {
                    Uncommon.valueOf(name)
                } catch (e: IllegalArgumentException) {
                    try {
                        Rare.valueOf(name)
                    } catch (e: IllegalArgumentException) {
                        try {
                            SuperRare.valueOf(name)
                        } catch (e: IllegalArgumentException) {
                            Log.e(this::class.java.canonicalName, "Cannot find character with name: ${name}")
                            e.printStackTrace()
                            ErrorCharacter
                        }
                    }
                }
            }
        }
    }
}