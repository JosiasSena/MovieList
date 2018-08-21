package com.josiassena.helpers.extensions

import android.widget.ImageView
import com.josiassena.helpers.R
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso

fun ImageView.setImageFromUrl(url: String) {
    Picasso.with(context)
            .load(url)
            .error(R.drawable.poster_place_holder)
            .placeholder(R.drawable.poster_place_holder)
            .into(this)
}

fun ImageView.setImageFromUrlOffLine(url: String) {
    Picasso.with(context)
            .load(url)
            .error(R.drawable.poster_place_holder)
            .placeholder(R.drawable.poster_place_holder)
            .networkPolicy(NetworkPolicy.OFFLINE)
            .into(this)
}