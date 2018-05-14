package com.josiassena.movielist.genres.view.rec_view

import android.content.Intent
import android.os.Bundle
import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.josiassena.core.Genre
import com.josiassena.movielist.R
import com.josiassena.movielist.app_helpers.constants.QUERY_KEY
import com.josiassena.movielist.app_helpers.constants.QueryTypes
import com.josiassena.movielist.movies.view.MoviesActivity
import kotlinx.android.synthetic.main.item_genres.view.*

const val KEY_GENRE = "key_genre_id"

class GenresViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView)

class GenresDiffListener : DiffUtil.ItemCallback<Genre>() {

    override fun areItemsTheSame(oldItem: Genre?, newItem: Genre?): Boolean {
        return oldItem?.id == newItem?.id
    }

    override fun areContentsTheSame(oldItem: Genre?, newItem: Genre?): Boolean {
        return oldItem == newItem
    }
}

class GenresAdapter : ListAdapter<Genre, GenresViewHolder>(GenresDiffListener()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenresViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_genres, parent, false)
        return GenresViewHolder(view)
    }

    override fun onBindViewHolder(holder: GenresViewHolder, position: Int) {
        val genre = getItem(position)
        val itemView = holder.itemView
        val tvGenreTitle = itemView?.tvGenreTitle

        tvGenreTitle?.text = genre.name

        itemView?.setOnClickListener {
            goToMoviesActivity(tvGenreTitle, genre)
        }
    }

    private fun goToMoviesActivity(tvGenreTitle: TextView?, genre: Genre) {
        val context = tvGenreTitle?.context

        context?.let {
            val intent = Intent(it, MoviesActivity::class.java).apply {
                val bundle = Bundle().apply {
                    putString(QUERY_KEY, QueryTypes.GENRE.name)
                    putParcelable(KEY_GENRE, genre)
                }

                putExtras(bundle)
            }

            it.startActivity(intent)
        }
    }

}