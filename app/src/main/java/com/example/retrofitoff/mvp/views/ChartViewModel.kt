package com.example.retrofitoff.mvp.views

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofitoff.model.StarGroup
import com.example.retrofitoff.data.repository.Repository
import com.example.retrofitoff.mvp.EnumRange

import kotlinx.coroutines.launch
import java.io.IOException


class ChartViewModel(
    private val repository: Repository,


    ) : ViewModel() {

    val chartResponse = MutableLiveData<List<StarGroup>>()
    val error = MutableLiveData<String>()

    fun getReposStars(userName: String, repoName: String, groupType: EnumRange.Companion.GroupType, dateSelected: Int) {
        viewModelScope.launch {
            try {
                val response: List<StarGroup> =
                    repository.getStarRepo(userName, repoName, groupType, dateSelected)
               // Log.d("CHART_VIEW_RESP", "Exception: ${response[0].starredAt}")

                chartResponse.value = response
            } catch (e: IOException) {
                Log.d("EXCEPTION_CHART_VIEW", "Exception: $e")
                Log.d("EXCEPTION_CHART_VIEW", "Exception: ${e.message}")
                Log.d("EXCEPTION_CHART_VIEW", "Exception: ${e.localizedMessage?.hashCode()}")

                if (e.localizedMessage?.hashCode() == 964672022) {
                    error.value = "Отсутствует подключение к интернету"
                }

            } catch (e: Exception) {
                Log.d("EXCEPTION_CHART_VIEW", "Exception: $e")
                Log.d("EXCEPTION_CHART_VIEW", "Exception: ${e.message}")
                Log.d("EXCEPTION_CHART_VIEW", "Exception: ${e.localizedMessage?.hashCode()}")

                if (e.localizedMessage?.hashCode() == -1358142879) {
                    error.value = "Лимит запросов закончился"
                }
            }
        }
    }

}