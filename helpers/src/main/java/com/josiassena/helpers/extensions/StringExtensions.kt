package com.josiassena.helpers.extensions

private const val YOUTUBE_BASE_URL = "https://www.youtube.com/watch?v="

fun String.toYoutubeUrl(): String {
    return YOUTUBE_BASE_URL + this
}