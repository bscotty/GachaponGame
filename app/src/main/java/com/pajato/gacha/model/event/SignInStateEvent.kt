package com.pajato.gacha.model.event

import com.pajato.gacha.ui.SignInState

class SignInStateEvent(private val state: SignInState) : Event {
    override fun getData(): SignInState {
        return state
    }

}