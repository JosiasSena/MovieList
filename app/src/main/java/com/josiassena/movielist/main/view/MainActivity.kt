package com.josiassena.movielist.main.view

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.MenuItem
import com.hannesdorfmann.mosby.mvp.MvpActivity
import com.josiassena.movielist.R
import com.josiassena.movielist.app.App
import com.josiassena.movielist.genres.view.GenreFragment
import com.josiassena.movielist.home.view.HomeFragment
import com.josiassena.movielist.main.presenter.MainPresenter
import com.josiassena.movielist.settings.view.SettingsActivity
import com.rapidsos.helpers.extensions.hide
import com.rapidsos.helpers.extensions.setImageFromUrl
import com.rapidsos.helpers.extensions.show
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.nav_header_main.view.*

class MainActivity : MvpActivity<View, MainPresenter>(),
        NavigationView.OnNavigationItemSelectedListener, View {

    private lateinit var header: android.view.View

    override fun createPresenter() = MainPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        App.component.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        initNavigationDrawer()

        goHome()
    }

    private fun initNavigationDrawer() {
        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener(this)

        handleNavigationDrawerHeader()
    }

    private fun handleNavigationDrawerHeader() {
        header = navView.getHeaderView(0)

        presenter.getCurrentUser()?.let {
            header.tvFullName.text = it.displayName
            header.tvEmail.text = it.email
            header.ivProfilePic.setImageFromUrl(it.photoUrl?.toString() as String)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_home -> {
                genreSearchView.hide()

                goHome()
            }
            R.id.nav_genres -> {
                genreSearchView.show()

                goToGenresFragment()
            }
            R.id.nav_settings -> SettingsActivity.start(this)
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun goHome() {
        supportFragmentManager
                ?.beginTransaction()
                ?.replace(R.id.content_main, HomeFragment.newInstance())
                ?.commit()
    }

    private fun goToGenresFragment() {
        supportFragmentManager
                ?.beginTransaction()
                ?.replace(R.id.content_main, GenreFragment.newInstance())
                ?.commit()
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}
