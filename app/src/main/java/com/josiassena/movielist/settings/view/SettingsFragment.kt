package com.josiassena.movielist.settings.view

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseUser
import com.hannesdorfmann.mosby.mvp.MvpFragment
import com.josiassena.movielist.R
import com.josiassena.movielist.app.App
import com.josiassena.movielist.settings.presenter.SettingsPresenter
import com.rapidsos.helpers.extensions.hide
import com.rapidsos.helpers.extensions.setImageFromUrl
import com.rapidsos.helpers.extensions.show
import kotlinx.android.synthetic.main.fragment_settings.*
import java.util.*
import javax.inject.Inject

class SettingsFragment : MvpFragment<View, SettingsPresenter>(), View {

    private val authUi by lazy { AuthUI.getInstance() }
    private val providers = Arrays.asList(AuthUI.IdpConfig.GoogleBuilder().build())

    @Inject
    lateinit var settingsPresenter: SettingsPresenter

    companion object {
        private const val TAG = "SettingsFragment"
        private const val RC_SIGN_IN = 5124

        fun newInstance() = SettingsFragment()
    }

    override fun createPresenter() = settingsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.component.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): android.view.View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onStart() {
        super.onStart()

        val currentUser = presenter.getCurrentUser()
        presenter.updateCurrentUserData(currentUser)

        btnGoogleSignIn.setOnClickListener { signIn() }
        btnSignOut.setOnClickListener { signOut() }
    }

    private fun signIn() {
        val signInIntent = authUi.createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build()

        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    val currentUser = presenter.getCurrentUser()

                    presenter.updateCurrentUserData(currentUser)

                    showSignOutButton()

                } catch (apiException: ApiException) {
                    // Google Sign In failed, update UI appropriately
                    Log.e(TAG, "Google sign in failed.", apiException)
                }
            } else {
                if (response == null) {
                    Log.e(TAG, "Google sign in failed: Canceled by user.")
                } else {
                    val error = response.error

                    Log.e(TAG, "Google sign in failed with error code: ${error?.errorCode}")
                    Log.e(TAG, "Google sign in failed: ${error?.localizedMessage}")
                }
            }
        }
    }

    private fun signOut() {
        context?.let {
            authUi.signOut(it)
                    .addOnCompleteListener {

                        showSignInButton()

                        presenter.onSignedOut()
                    }
        }
    }

    override fun displayUserData(currentUser: FirebaseUser) {
        cvProfileInformation.show()

        ivProfilePicture.setImageFromUrl(currentUser.photoUrl?.toString() as String)
        tvFullName.text = currentUser.displayName
        tvEmailAddress.text = currentUser.email
    }

    override fun hideUserData() {
        cvProfileInformation.hide()
    }

    override fun showSignOutButton() {
        btnGoogleSignIn.hide()
        btnSignOut.show()
    }

    override fun showSignInButton() {
        btnSignOut.hide()
        btnGoogleSignIn.show()
    }

}
