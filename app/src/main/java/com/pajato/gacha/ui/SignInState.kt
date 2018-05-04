package com.pajato.gacha.ui

import android.view.View
import android.widget.TextView
import com.pajato.gacha.R

sealed class SignInState {
    abstract fun update(textView: TextView)

    sealed class ErrorState(private val errorMessageResourceId: Int) : SignInState() {
        override fun update(textView: TextView) {
            textView.text = textView.context.getString(errorMessageResourceId)
            textView.visibility = View.VISIBLE
        }

        class NoEmail : ErrorState(R.string.errorNoEmail)
        class NoPassword : ErrorState(R.string.errorNoPassword)
        class InvalidEmail : ErrorState(R.string.errorInvalidEmail)
        class InvalidPassword : ErrorState(R.string.errorInvalidPassword)
        class EmailCollision : ErrorState(R.string.errorEmailCollision)
        class MiscError : ErrorState(R.string.errorGenericMessage)
    }

    class SuccessfulState(private val visibility: Int = View.INVISIBLE) : SignInState() {
        override fun update(textView: TextView) {
            textView.visibility = visibility
            textView.text = textView.context.getString(R.string.errorGenericMessage)
        }
    }

}