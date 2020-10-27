package ru.jdeveloperapps.filmnews.repositories

import ru.jdeveloperapps.filmnews.network.RetrofitInstance

class MostPopularTVShowRepository {

    suspend fun getMostPopularTvShows(pageNumber: Int) =
        RetrofitInstance.api.getMostPopularTV(pageNumber)

}