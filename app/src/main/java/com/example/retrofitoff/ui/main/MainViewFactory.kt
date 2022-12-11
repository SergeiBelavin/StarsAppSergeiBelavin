package com.example.retrofitoff.ui.main


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.example.retrofitoff.data.repository.Repository
import com.example.retrofitoff.ui.main.view.MainViewModel


class MainViewFactory(private val repository: Repository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
      return MainViewModel(repository) as T
    }
    }




