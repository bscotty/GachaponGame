package com.pajato.gacha.model.character

import com.pajato.gacha.R

enum class Rarity(val rarity: Int, val backgroundDrawableId: Int) {
    COMMON(0, R.drawable.gradient_common),
    UNCOMMON(1, R.drawable.gradient_uncommon),
    RARE(2, R.drawable.gradient_rare),
    SUPER_RARE(3, R.drawable.gradient_super_rare)
}