package com.josiassena.movielist.main.view

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.view.MenuItem
import android.view.View
import com.hannesdorfmann.mosby.mvp.MvpActivity
import com.josiassena.helpers.extensions.hide
import com.josiassena.helpers.extensions.setImageFromUrl
import com.josiassena.helpers.extensions.show
import com.josiassena.helpers.extensions.showLongSnackBar
import com.josiassena.movielist.R
import com.josiassena.movielist.app.App
import com.josiassena.movielist.favorite_movies.view.FavoritesFragment
import com.josiassena.movielist.genres.view.GenreFragment
import com.josiassena.movielist.home.view.HomeFragment
import com.josiassena.movielist.main.presenter.MainPresenter
import com.josiassena.movielist.settings.view.SettingsFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.nav_header_main.view.*
import javax.inject.Inject

class MainActivity : MvpActivity<MainView, MainPresenter>(),
        NavigationView.OnNavigationItemSelectedListener, MainView {

    @Inject
    lateinit var mainPresenter: MainPresenter

    private lateinit var navigationDrawerHeader: View

    override fun createPresenter() = mainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        App.component.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        initNavigationDrawer()
    }

    private fun initNavigationDrawer() {
        val toggle = ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener(this)

        initNavigationDrawerHeader()

        goHome()
    }

    private fun initNavigationDrawerHeader() {
        navigationDrawerHeader = navView.getHeaderView(0)

        populateNavDrawerHeader()
    }

    override fun populateNavDrawerHeader() {
        presenter.getCurrentUser()?.let {
            navigationDrawerHeader.tvFullName.text = it.displayName
            navigationDrawerHeader.tvEmail.text = it.email
            navigationDrawerHeader.ivProfilePic.setImageFromUrl(it.photoUrl?.toString() as String)
        }
    }

    override fun resetNavDrawerHeader() {
        navigationDrawerHeader.tvFullName.text = resources.getString(R.string.nav_header_title)
        navigationDrawerHeader.tvEmail.text = resources.getString(R.string.nav_header_subtitle)
        navigationDrawerHeader.ivProfilePic.setImageResource(R.mipmap.ic_launcher)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var fragmentToGoTo: Fragment? = null

        drawerLayout.addDrawerListener(object : DrawerLayout.DrawerListener {

            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
            }

            override fun onDrawerOpened(drawerView: View) {
            }

            override fun onDrawerClosed(drawerView: View) {
                fragmentToGoTo?.let {
                    goToFragment(it)

                    fragmentToGoTo = null
                }
            }

            override fun onDrawerStateChanged(newState: Int) {
            }
        })

        when (item.itemId) {
            R.id.nav_home -> {
                genreSearchView.hide()
                fragmentToGoTo = HomeFragment.newInstance()
            }
            R.id.nav_genres -> {
                genreSearchView.show()
                fragmentToGoTo = GenreFragment.newInstance()
            }
            R.id.nav_favorites -> {
                genreSearchView.hide()

                fragmentToGoTo = if (presenter.isSignedIn()) {
                    FavoritesFragment.newInstance()
                } else {
                    showLongSnackBar(navigationDrawerHeader, R.string.favorites_you_must_sign_in)
                    SettingsFragment.newInstance()
                }
            }
            R.id.nav_settings -> {
                genreSearchView.hide()
                fragmentToGoTo = SettingsFragment.newInstance()
            }
        }

        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun goHome() {
        navView.menu.getItem(0).isChecked = true
        goToFragment(HomeFragment.newInstance())
    }

    private fun goToFragment(fragment: Fragment) {
        supportFragmentManager
                ?.beginTransaction()
                ?.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                ?.replace(R.id.content_main, fragment)
                ?.commit()
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}
