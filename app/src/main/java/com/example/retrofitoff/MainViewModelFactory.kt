package com.example.retrofitoff


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras

import com.example.retrofitoff.repository.Repository



class MainViewModelFactory(private val repository: Repository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
      return MainViewModel(repository) as T
    }
    }




