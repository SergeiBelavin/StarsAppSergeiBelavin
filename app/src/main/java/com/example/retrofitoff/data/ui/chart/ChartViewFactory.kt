package com.example.retrofitoff.data.ui.chart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.retrofitoff.data.repository.Repository

class ChartViewFactory (private val repository: Repository,
):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ChartViewModel(repository,) as T
    }
}