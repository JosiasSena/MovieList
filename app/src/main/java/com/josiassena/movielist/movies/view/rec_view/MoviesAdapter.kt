package com.josiassena.movielist.movies.view.rec_view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.josiassena.core.Result
import com.josiassena.movielist.R
import com.josiassena.movielist.movie_info.view.MovieInfoActivity
import com.rapidsos.helpers.extensions.setImageFromUrl
import kotlinx.android.synthetic.main.item_movie.view.*
import org.jetbrains.anko.AnkoLogger

const val POSTER_BASE_URL = "https://image.tmdb.org/t/p/w500"
const val MOVIE_ID_KEY = "movie_id_key"

class MoviesViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView)

class MoviesAdapter : RecyclerView.Adapter<MoviesViewHolder>(), AnkoLogger {

    private val movies = arrayListOf<Result>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return MoviesViewHolder(layoutInflater.inflate(R.layout.item_movie, parent, false))
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val itemView = holder.itemView
        val movie = movies[position]

        itemView?.ivMoviePoster?.setImageFromUrl(POSTER_BASE_URL + movie.posterPath)
        itemView?.setOnClickListener { goToMovieInformationActivity(itemView, movie) }
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

    override fun getItemCount() = movies.size

    fun addMovies(results: List<Result>) {
        movies.clear()
        movies.addAll(results)

        notifyItemRangeChanged(0, itemCount)
    }

    fun addMoreMovies(results: List<Result>) {
        movies.addAll(itemCount, results)

        notifyItemInserted(itemCount)
    }
}