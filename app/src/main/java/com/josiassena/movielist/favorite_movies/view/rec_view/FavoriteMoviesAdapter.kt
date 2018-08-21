package com.josiassena.movielist.favorite_movies.view.rec_view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.josiassena.core.Result
import com.josiassena.helpers.extensions.setImageFromUrl
import com.josiassena.movielist.R
import com.josiassena.movielist.app_helpers.constants.MOVIE_ID_KEY
import com.josiassena.movielist.app_helpers.constants.POSTER_BASE_URL
import com.josiassena.movielist.movie_info.view.MovieInfoActivity
import kotlinx.android.synthetic.main.item_movie.view.*

class FavoriteMoviesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

class FavoriteMoviesAdapter : RecyclerView.Adapter<FavoriteMoviesViewHolder>() {

    private val favorites = arrayListOf<Result>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteMoviesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_movie, parent, false)
        return FavoriteMoviesViewHolder(view)
    }

    override fun getItemCount() = favorites.size

    override fun onBindViewHolder(holder: FavoriteMoviesViewHolder, position: Int) {
        val movie = favorites[position]

        holder.itemView?.let { itemView ->
            itemView.ivMoviePoster.setImageFromUrl(POSTER_BASE_URL + movie.posterPath)
            itemView.setOnClickListener { goToMovieInformationActivity(itemView, movie) }
        }
    }

    private fun goToMovieInformationActivity(itemView: View, movie: Result) {
        val context = itemView.context
        val ivMoviePoster = itemView.ivMoviePoster

        val transitionName = ivMoviePoster?.transitionName as String
        val options = ActivityOptionsCompat
                .makeSceneTransitionAnimation(context as Activity, ivMoviePoster, transitionName)

        val intent = Intent(context, MovieInfoActivity::class.java)
        intent.putExtras(Bundle().apply {
            putInt(MOVIE_ID_KEY, movie.id as Int)
        })

        context.startActivity(intent, options.toBundle())
    }

    fun addFavoriteMovies(movies: ArrayList<Result>) {
        if (favorites.isNotEmpty()) {
            favorites.clear()
        }

        favorites.addAll(movies)
        notifyDataSetChanged()
    }
}