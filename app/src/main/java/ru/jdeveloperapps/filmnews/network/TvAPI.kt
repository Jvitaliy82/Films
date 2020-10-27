package ru.jdeveloperapps.filmnews.network

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import ru.jdeveloperapps.filmnews.models.TVShow

interface TvAPI {

    @GET("most-popular")
    suspend fun getMostPopularTV(
        @Query("page") page: Int,
    ): Response<TVShow>

}