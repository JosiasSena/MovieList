package com.josiassena.movielist.top_rated_movies.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hannesdorfmann.mosby.mvp.MvpFragment
import com.josiassena.movielist.R
import com.josiassena.movielist.top_rated_movies.presenter.TopRatedMoviesPresenterImpl

class TopRatedMoviesFragment : MvpFragment<TopRatedMoviesView, TopRatedMoviesPresenterImpl>() {

    override fun createPresenter() = TopRatedMoviesPresenterImpl()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_top_rated_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.getTopRatedMovies()
    }

}
