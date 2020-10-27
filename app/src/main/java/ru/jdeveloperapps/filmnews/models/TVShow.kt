package ru.jdeveloperapps.filmnews.models

data class TVShow(
    val page: Int,
    val pages: Int,
    val total: String,
    val tv_shows: MutableList<TvShowX>
)