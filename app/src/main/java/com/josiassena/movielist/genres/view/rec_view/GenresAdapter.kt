package com.josiassena.movielist.genres.view.rec_view

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.josiassena.core.Genre
import com.josiassena.core.Genres
import com.josiassena.movielist.R
import com.josiassena.movielist.movies.view.MoviesActivity
import kotlinx.android.synthetic.main.item_genres.view.*

val KEY_GENRE = "key_genre_id"

class GenresViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView)

class GenresAdapter : RecyclerView.Adapter<GenresViewHolder>() {

    private val genres = arrayListOf<Genre>()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): GenresViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val view = layoutInflater.inflate(R.layout.item_genres, parent, false)
        return GenresViewHolder(view)
    }

    override fun onBindViewHolder(holder: GenresViewHolder?, position: Int) {
        val genre = genres[position]
        val itemView = holder?.itemView
        val tvGenreTitle = itemView?.tvGenreTitle

        tvGenreTitle?.text = genre.name

        itemView?.setOnClickListener {
            goToMoviesActivity(tvGenreTitle, genre)
        }
    }

    override fun getItemCount() = genres.size

    private fun goToMoviesActivity(tvGenreTitle: TextView?, genre: Genre) {
        val context = tvGenreTitle?.context

        context?.let {
            val bundle = Bundle()
            bundle.putParcelable(KEY_GENRE, genre)

            val intent = Intent(it, MoviesActivity::class.java)
            intent.putExtras(bundle)

            it.startActivity(intent)
        }
    }

    fun updateGenres(genre: Genres) {
        val position = genres.size
        genres.clear()
        genres.addAll(genre.genres)
        notifyItemInserted(position)
    }
}