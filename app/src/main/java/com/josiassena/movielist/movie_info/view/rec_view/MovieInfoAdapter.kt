package com.josiassena.movielist.movie_info.view.rec_view

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.josiassena.core.MovieVideosResult
import com.josiassena.movielist.R
import com.josiassena.movielist.movie_info.presenter.MovieInfoPresenterImpl

class MovieInfoViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView)

class MovieInfoAdapter(private val presenter: MovieInfoPresenterImpl) : RecyclerView.Adapter<MovieInfoViewHolder>() {

    private val previews = arrayListOf<MovieVideosResult>()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MovieInfoViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val view = layoutInflater.inflate(R.layout.item_movie_info_preview, parent, false)
        return MovieInfoViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieInfoViewHolder?, position: Int) {
        val itemView = holder?.itemView
        val preview = previews[position]

        itemView?.findViewById<TextView>(R.id.tvPreviewTitle)?.text = preview.name
        itemView?.setOnClickListener { presenter.playVideoFromPreview(preview) }
    }

    override fun getItemCount() = previews.size

    fun setPreviews(result: List<MovieVideosResult>) {
        previews.clear()
        previews.addAll(result)
        notifyDataSetChanged()
    }
}