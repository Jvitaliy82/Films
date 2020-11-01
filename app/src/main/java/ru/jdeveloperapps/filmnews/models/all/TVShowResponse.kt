package ru.jdeveloperapps.filmnews.models.all

data class TVShowResponse(
    val page: Int,
    val pages: Int,
    val total: Int,
    val tv_shows: MutableList<TvShowX>
)