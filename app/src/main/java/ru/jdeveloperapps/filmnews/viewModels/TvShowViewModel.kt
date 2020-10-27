package ru.jdeveloperapps.filmnews.viewModels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Response
import ru.jdeveloperapps.filmnews.models.TVShow
import ru.jdeveloperapps.filmnews.other.Resourse
import ru.jdeveloperapps.filmnews.repositories.MostPopularTVShowRepository

class TvShowViewModel @ViewModelInject constructor(val repository: MostPopularTVShowRepository) :
    ViewModel() {

    val mostPopularTVShow: MutableLiveData<Resourse<TVShow>> = MutableLiveData()
    var mostPopularPage = 1
    var mostPopularResponse: TVShow? = null

    fun getMostPopularTv() = viewModelScope.launch {
        mostPopularTVShow.postValue(Resourse.Loading())
        val response = repository.getMostPopularTvShows(mostPopularPage)
        mostPopularTVShow.postValue(handleMostPopularTv(response))
    }

    private fun handleMostPopularTv(response: Response<TVShow>): Resourse<TVShow>? {
        if (response.isSuccessful) {
            response.body()?.let {resultResponce ->
                mostPopularPage++
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


}