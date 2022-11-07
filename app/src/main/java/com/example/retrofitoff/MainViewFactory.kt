package com.example.retrofitoff


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.example.retrofitoff.repository.Repository



class MainViewFactory(private val repository: Repository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
      return MainView(repository) as T
    }
    }




