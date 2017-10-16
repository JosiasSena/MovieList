package com.rapidsos.helpers.extensions

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.widget.ImageView
import com.rapidsos.helpers.R
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso

fun ImageView.setImageFromUrl(url: String) {
    Picasso.with(context)
            .load(url)
            .error(R.drawable.ic_terrain)
            .noPlaceholder()
            .into(this)
}

fun ImageView.setImageFromUrlOffLine(url: String) {
    Picasso.with(context)
            .load(url)
            .error(R.drawable.ic_terrain)
            .networkPolicy(NetworkPolicy.OFFLINE)
            .noPlaceholder()
            .into(this)
}

fun ImageView.getBitmap(): Bitmap? {
    drawable?.let {
        return (it as BitmapDrawable).bitmap
    }

    return null
}