package com.josiassena.movielist.home.view

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hannesdorfmann.mosby.mvp.MvpFragment
import com.josiassena.core.Result
import com.josiassena.movielist.R
import com.josiassena.movielist.app_helpers.observers.HomeLifeCycleObserver
import com.josiassena.movielist.home.presenter.HomePresenterImpl
import com.josiassena.movielist.home.view.rec_views.movies.HomeMoviesAdapter
import kotlinx.android.synthetic.main.main_now_playing_section.*
import kotlinx.android.synthetic.main.main_top_movies_section.*
import kotlinx.android.synthetic.main.main_top_tv_shows_section.*
import org.jetbrains.anko.AnkoLogger

class HomeFragment : MvpFragment<HomeView, HomePresenterImpl>(), HomeView, AnkoLogger {

    private val topMoviesAdapter = HomeMoviesAdapter()
    private val nowPlayingAdapter = HomeMoviesAdapter()
    private val upcomingMoviesAdapter = HomeMoviesAdapter()

    override fun createPresenter() = HomePresenterImpl()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        lifecycle.addObserver(HomeLifeCycleObserver)

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initTopMoviesRecView()

        initUpcomingMoviesRecView()

        initNowPlayingRecView()
    }

    private fun initTopMoviesRecView() {
        rvTopMovies.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rvTopMovies.adapter = topMoviesAdapter

        presenter.getTopRatedMovies()
    }

    private fun initUpcomingMoviesRecView() {
        rvUpcomingMovies.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rvUpcomingMovies.adapter = upcomingMoviesAdapter

        presenter.getUpcomingMovies()
    }

    private fun initNowPlayingRecView() {
        rvNowPlaying.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rvNowPlaying.adapter = nowPlayingAdapter

        presenter.getNowPlayingMovies()
    }

    override fun onGotTopRatedMovies(top5: List<Result>) {
        topMoviesAdapter.submitList(top5)
    }

    override fun onGotNowPlayingMovies(top5: List<Result>) {
        nowPlayingAdapter.submitList(top5)
    }

    override fun onGotUpcomingMovies(top5: List<Result>) {
        upcomingMoviesAdapter.submitList(top5)
    }

}
