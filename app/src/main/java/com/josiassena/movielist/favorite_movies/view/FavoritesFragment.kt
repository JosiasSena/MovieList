package com.josiassena.movielist.favorite_movies.view

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hannesdorfmann.mosby.mvp.MvpFragment
import com.josiassena.movielist.R
import com.josiassena.movielist.favorite_movies.presenter.FavoritesPresenterImpl
import com.josiassena.movielist.favorite_movies.view.rec_view.FavoriteMoviesAdapter
import kotlinx.android.synthetic.main.fragment_favorites.*

class FavoritesFragment : MvpFragment<FavoritesView, FavoritesPresenterImpl>(), FavoritesView {

    override fun createPresenter() = FavoritesPresenterImpl()

    private val recViewAdapter by lazy { FavoriteMoviesAdapter() }

    companion object {
        fun newInstance() = FavoritesFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecView()

        presenter.getFavoriteMovies()
    }

    private fun initRecView() {
        rvFavoriteMovies.adapter = recViewAdapter
        rvFavoriteMovies.layoutManager = GridLayoutManager(context, 2)
    }

    override fun goToLogInScreen() {
    }

    override fun showEmptyListView() {
    }

}
