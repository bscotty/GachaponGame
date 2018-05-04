package com.pajato.gacha

import android.arch.lifecycle.LifecycleOwner
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.pajato.gacha.database.FirebaseLoginManager
import com.pajato.gacha.model.event.RxBus
import com.pajato.gacha.model.event.SignInStateEvent
import com.pajato.gacha.ui.SignInState.SuccessfulState
import com.pajato.gacha.ui.SignInState.ErrorState.*
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.android.synthetic.main.layout_new_user.*
import kotlinx.android.synthetic.main.layout_new_user.view.*
import kotlinx.android.synthetic.main.layout_sign_in_options.*


class SignInActivity : AppCompatActivity(), LifecycleOwner, Consumer<SignInStateEvent> {
    private lateinit var subscription: Disposable

    override fun accept(t: SignInStateEvent) {
        val state = t.getData()
        state.update(errorMessage)
        when (state) {
            is SuccessfulState -> finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        this.lifecycle.addObserver(FirebaseLoginManager)

        signInEmailLayout.setOnClickListener {
            signInEmail()
        }

        signInGoogleLayout.setOnClickListener {
            signInGoogle()
        }

        signInFacebookLayout.setOnClickListener {
            signInFacebook()
        }

        signInTwitterLayout.setOnClickListener {
            signInTwitter()
        }
    }

    override fun onResume() {
        super.onResume()
        subscription = RxBus.subscribeToEventType(SignInStateEvent::class.java, this)
    }

    override fun onPause() {
        super.onPause()
        subscription.dispose()
    }

    /** Initiate Firebase sign in with email and password. */
    private fun signInEmail() {
        signInFrame.removeAllViews()
        val v = layoutInflater.inflate(R.layout.layout_new_user, signInFrame)
        v.emailPasswordSignInButton.setOnClickListener { _ ->
            val email = inputEmail.text.toString()
            val password = inputPassword.text.toString()
            when {
                email == "" -> {
                    RxBus.send(SignInStateEvent(NoEmail()))
                }
                password == "" -> {
                    RxBus.send(SignInStateEvent(NoPassword()))
                }
                else -> signInAndCatchErrors(email, password)
            }
        }
    }

    /** Call the sign in and catch various errors. */
    private fun signInAndCatchErrors(email: String, password: String) {
        FirebaseLoginManager.createUser(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                RxBus.send(SignInStateEvent(SuccessfulState()))
            } else {
                when (task.exception) {
                    is FirebaseAuthWeakPasswordException -> RxBus.send(SignInStateEvent(InvalidPassword()))
                    is FirebaseAuthInvalidCredentialsException -> RxBus.send(SignInStateEvent(InvalidEmail()))

                    is FirebaseAuthUserCollisionException -> {
                        // On a collision, attempt a sign in.
                        FirebaseLoginManager.signInWithEmailAndPassword(email, password)
                                .addOnCompleteListener { secondTask ->
                                    if (secondTask.isSuccessful) {
                                        RxBus.send(SignInStateEvent(SuccessfulState()))
                                    } else {
                                        RxBus.send(SignInStateEvent(EmailCollision()))
                                    }
                                }
                    }

                    else -> RxBus.send(SignInStateEvent(MiscError()))
                }
            }
        }
    }

    private fun signInGoogle() {
        Snackbar.make(signInFrame, "Signing in with Google is a feature in progress! Thanks for your patience.", Snackbar.LENGTH_LONG)
                .show()
    }

    private fun signInFacebook() {
        Snackbar.make(signInFrame, "Signing in with Facebook is a feature in progress! Thanks for your patience.", Snackbar.LENGTH_LONG)
                .show()
    }

    private fun signInTwitter() {
        Snackbar.make(signInFrame, "Signing in with Twitter is a feature in progress! Thanks for your patience.", Snackbar.LENGTH_LONG)
                .show()
    }

}