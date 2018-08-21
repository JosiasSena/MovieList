package com.josiassena.movielist.favorite_movies.view.rec_view

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.josiassena.core.Result
import com.josiassena.movielist.R

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
    }
}