package com.josiassena.movielist.home.view

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hannesdorfmann.mosby.mvp.MvpFragment
import com.josiassena.core.Result
import com.josiassena.movielist.R
import com.josiassena.movielist.app_helpers.constants.QUERY_KEY
import com.josiassena.movielist.app_helpers.constants.QueryTypes
import com.josiassena.movielist.app_helpers.observers.fragment.HomeLifeCycleObserver
import com.josiassena.movielist.home.presenter.HomePresenterImpl
import com.josiassena.movielist.home.view.rec_views.movies.HomeMoviesAdapter
import com.josiassena.movielist.movies.view.MoviesActivity
import com.josiassena.helpers.extensions.hide
import com.josiassena.helpers.extensions.show
import kotlinx.android.synthetic.main.main_now_playing_section.*
import kotlinx.android.synthetic.main.main_top_movies_section.*
import kotlinx.android.synthetic.main.main_upcoming_movies_section.*
import org.jetbrains.anko.AnkoLogger

class HomeFragment : MvpFragment<HomeView, HomePresenterImpl>(), HomeView, AnkoLogger {

    private val topMoviesAdapter = HomeMoviesAdapter()
    private val nowPlayingAdapter = HomeMoviesAdapter()
    private val upcomingMoviesAdapter = HomeMoviesAdapter()

    companion object {
        fun newInstance() = HomeFragment()
    }

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

        headerTopMovies.setOnClickListener {
            goToMoviesActivity(QueryTypes.TOP_MOVIES.name)
        }

        headerUpcomingMovies.setOnClickListener {
            goToMoviesActivity(QueryTypes.UPCOMING_MOVIES.name)
        }

        headerNowPlaying.setOnClickListener {
            goToMoviesActivity(QueryTypes.NOW_PLAYING.name)
        }

        getMovies()
    }

    private fun getMovies() {
        presenter.getTop5RatedMovies()
        presenter.get5UpcomingMovies()
        presenter.get5NowPlayingMovies()
    }

    private fun goToMoviesActivity(queryType: String) {
        context?.let {
            val bundle = Bundle().apply {
                putString(QUERY_KEY, queryType)
            }

            val intent = Intent(it, MoviesActivity::class.java).apply {
                putExtras(bundle)
            }

            it.startActivity(intent)
        }
    }

    private fun initTopMoviesRecView() {
        rvTopMovies.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rvTopMovies.adapter = topMoviesAdapter
    }

    private fun initUpcomingMoviesRecView() {
        rvUpcomingMovies.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rvUpcomingMovies.adapter = upcomingMoviesAdapter
    }

    private fun initNowPlayingRecView() {
        rvNowPlaying.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rvNowPlaying.adapter = nowPlayingAdapter
    }

    override fun onGotTopRatedMovies(top5: List<Result>) = topMoviesAdapter.addItems(top5)

    override fun onGotUpcomingMovies(top5: List<Result>) = upcomingMoviesAdapter.addItems(top5)

    override fun onGotNowPlayingMovies(top5: List<Result>) = nowPlayingAdapter.addItems(top5)

    override fun displayTop5EmptyStateView() {
        rvTopMovies.hide()
        emptyStateTop5.show()
    }

    override fun displayUpcomingEmptyStateView() {
        rvUpcomingMovies.hide()
        emptyStateUpcomingMovies.show()
    }

    override fun displayNowPlayingEmptyStateView() {
        rvNowPlaying.hide()
        emptyStateNowPlaying.show()
    }

    override fun dismissTop5EmptyStateView() {
        emptyStateTop5.hide()
        rvTopMovies.show()
    }

    override fun dismissUpcomingEmptyStateView() {
        emptyStateUpcomingMovies.hide()
        rvUpcomingMovies.show()
    }

    override fun dismissNowPlayingEmptyStateView() {
        emptyStateNowPlaying.hide()
        rvNowPlaying.show()
    }

}
