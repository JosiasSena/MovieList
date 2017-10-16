package com.josiassena.movielist.splash

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.josiassena.movielist.R
import com.josiassena.movielist.genres.view.GenreActivity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask
import java.util.concurrent.TimeUnit

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportActionBar?.hide()

        Observable.timer(300, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    startActivity(intentFor<GenreActivity>().clearTask().newTask())
                })
    }
}
