package com.example.retrofitoff

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.retrofitoff.mode2.CharViewModel
import com.example.retrofitoff.repository.Repository

class CharViewModelFactory (private val repository: Repository
):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CharViewModel(repository) as T
    }
}