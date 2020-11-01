package ru.jdeveloperapps.filmnews.viewModels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Response
import ru.jdeveloperapps.filmnews.models.all.TVShowResponse
import ru.jdeveloperapps.filmnews.models.detail.TVDetailResponse
import ru.jdeveloperapps.filmnews.other.Resourse
import ru.jdeveloperapps.filmnews.repositories.MostPopularTVShowRepository

class TvShowViewModel @ViewModelInject constructor(val repository: MostPopularTVShowRepository) :
    ViewModel() {

    val mostPopularTVLiveData: MutableLiveData<Resourse<TVShowResponse>> = MutableLiveData()
    var currentPage = 1
    var totalAvailablePages = 1
    var mostPopularResponse: TVShowResponse? = null

    val showDetailLiveData: MutableLiveData<Resourse<TVDetailResponse>> = MutableLiveData()

    init {
        getMostPopularTv()
    }

    fun getMostPopularTv() = viewModelScope.launch {
        mostPopularTVLiveData.postValue(Resourse.Loading())
        val response = repository.getMostPopularTvShows(currentPage)
        mostPopularTVLiveData.postValue(handleMostPopularTv(response))
    }

    fun getDetail(showId: String) = viewModelScope.launch {
        showDetailLiveData.postValue(Resourse.Loading())
        val response = repository.getDetail(showId)
        showDetailLiveData.postValue(handleShowDetail(response))
    }

    private fun handleMostPopularTv(response: Response<TVShowResponse>): Resourse<TVShowResponse>? {
        if (response.isSuccessful) {
            response.body()?.let {resultResponce ->
                currentPage++
                totalAvailablePages = resultResponce.total
                if (mostPopularResponse == null) {
                    mostPopularResponse = resultResponce
                } else {
                    val oldTvShows = mostPopularResponse?.tv_shows
                    val newTvShows = resultResponce.tv_shows
                    oldTvShows?.addAll(newTvShows)
                }
                return Resourse.Success(mostPopularResponse ?: resultResponce)
            }
        }
        return Resourse.Error(response.message())
    }

    private fun handleShowDetail(response: Response<TVDetailResponse>): Resourse<TVDetailResponse>? {
        if (response.isSuccessful) {
            response.body()?.let {resultResponse ->
                return Resourse.Success(resultResponse)
            }
        }
        return Resourse.Error(response.message())
    }
}