package com.josiassena.movielist.home.view.rec_views.movies

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.josiassena.core.Result
import com.josiassena.movielist.R
import com.josiassena.movielist.app_helpers.constants.MOVIE_ID_KEY
import com.josiassena.movielist.app_helpers.constants.POSTER_BASE_URL
import com.josiassena.movielist.movie_info.view.MovieInfoActivity
import com.rapidsos.helpers.extensions.setImageFromUrl
import kotlinx.android.synthetic.main.item_main_list_view.view.*

class HomeMoviesViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView)

class HomeMoviesDiffListener : DiffUtil.ItemCallback<Result>() {

    override fun areItemsTheSame(oldItem: Result?, newItem: Result?): Boolean {
        return oldItem?.id == newItem?.id
    }

    override fun areContentsTheSame(oldItem: Result?, newItem: Result?): Boolean {
        return oldItem == newItem
    }
}

/**
 * @author Josias Sena
 */
class HomeMoviesAdapter : ListAdapter<Result, HomeMoviesViewHolder>(HomeMoviesDiffListener()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeMoviesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_main_list_view, parent, false)
        return HomeMoviesViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeMoviesViewHolder, position: Int) {
        val movie = getItem(position)

        holder.itemView?.let {
            it.iv_movie_poster.setImageFromUrl(POSTER_BASE_URL + movie.posterPath)
            it.setOnClickListener { goToMovieInformationActivity(it, movie) }
        }
    }

    private fun goToMovieInformationActivity(itemView: View, movie: Result) {
        val context = itemView.context
        val ivMoviePoster = itemView.iv_movie_poster

        val transitionName = ivMoviePoster?.transitionName as String
        val options = ActivityOptionsCompat
                .makeSceneTransitionAnimation(context as Activity, ivMoviePoster, transitionName)

        val intent = Intent(context, MovieInfoActivity::class.java)
        intent.putExtras(Bundle().apply {
            putInt(MOVIE_ID_KEY, movie.id as Int)
        })

        context.startActivity(intent, options.toBundle())
    }

}