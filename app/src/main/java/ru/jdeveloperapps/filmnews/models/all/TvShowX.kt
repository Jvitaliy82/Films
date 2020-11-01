package ru.jdeveloperapps.filmnews.models.all

data class TvShowX(
    val id: String,
    val name: String,
    val start_date: String,
    val country: String,
    val network: String,
    val status: String,
    val end_date: Any,
    val image_thumbnail_path: String,
    val permalink: String
)