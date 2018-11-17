package com.josiassena.movielist.splash

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.josiassena.movielist.R
import com.josiassena.movielist.main.view.MainActivity
import kotlinx.android.synthetic.main.activity_splash.*
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportActionBar?.hide()

        val appNameAnimation = AnimationUtils.loadAnimation(this, R.anim.splash_appname_animation)
        appNameAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {
            }

            override fun onAnimationStart(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
                startLogoAnimation()
            }
        })

        tvAppName.startAnimation(appNameAnimation)
    }

    private fun startLogoAnimation() {
        val logoAnimation = AnimationUtils.loadAnimation(this@SplashActivity, R.anim.splash_logo_animation)
        logoAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {
            }

            override fun onAnimationStart(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
                startActivity(intentFor<MainActivity>().clearTask().newTask())
            }
        })

        ivLogo.startAnimation(logoAnimation)
    }
}
