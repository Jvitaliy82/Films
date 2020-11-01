package ru.jdeveloperapps.filmnews.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import ru.jdeveloperapps.filmnews.models.all.TVShowResponse
import ru.jdeveloperapps.filmnews.models.detail.TVDetailResponse

interface TvAPI {

    @GET("most-popular")
    suspend fun getMostPopularTV(
        @Query("page") page: Int,
    ): Response<TVShowResponse>

    @GET("show-details")
    suspend fun getDetails(
        @Query("q") tvShowId: String,
    ): Response<TVDetailResponse>

}