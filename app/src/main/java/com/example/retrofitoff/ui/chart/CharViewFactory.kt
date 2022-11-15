package com.example.retrofitoff.ui.chart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.retrofitoff.ChartView
import com.example.retrofitoff.data.repository.Repository

class CharViewFactory (private val repository: Repository
):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ChartView(repository) as T
    }
}