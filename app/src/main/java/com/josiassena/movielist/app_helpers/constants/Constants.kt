package com.josiassena.movielist.app_helpers.constants

const val POSTER_BASE_URL = "https://image.tmdb.org/t/p/w500"
const val MOVIE_ID_KEY = "movie_id_key"
const val QUERY_KEY = "query"

enum class QueryTypes {
    GENRE,
    TOP_MOVIES,
    UPCOMING_MOVIES,
    NOW_PLAYING
}