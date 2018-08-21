package com.josiassena.movielist.home.view.rec_views.movies

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
import kotlinx.android.synthetic.main.item_main_list_view.view.*

class HomeMoviesViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView)

class HomeMoviesAdapter : RecyclerView.Adapter<HomeMoviesViewHolder>() {

    private val results = arrayListOf<Result>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeMoviesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_main_list_view, parent, false)
        return HomeMoviesViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeMoviesViewHolder, position: Int) {
        val movie = results[position]

        holder.itemView?.let {itemView ->
            itemView.ivHomeMoviePoster.setImageFromUrl(POSTER_BASE_URL + movie.posterPath)
            itemView.setOnClickListener { goToMovieInformationActivity(itemView, movie) }
        }
    }

    private fun goToMovieInformationActivity(itemView: View, movie: Result) {
        val context = itemView.context
        val ivMoviePoster = itemView.ivHomeMoviePoster

        val transitionName = ivMoviePoster?.transitionName as String
        val options = ActivityOptionsCompat
                .makeSceneTransitionAnimation(context as Activity, ivMoviePoster, transitionName)

        val intent = Intent(context, MovieInfoActivity::class.java)
        intent.putExtras(Bundle().apply {
            putInt(MOVIE_ID_KEY, movie.id as Int)
        })

        context.startActivity(intent, options.toBundle())
    }

    override fun getItemCount() = results.size

    fun addItems(items: List<Result>) {
        if (results.isNotEmpty()) {
            results.clear()
            notifyDataSetChanged()
        }

        results.addAll(items)
        notifyItemRangeInserted(0, itemCount)
    }

}