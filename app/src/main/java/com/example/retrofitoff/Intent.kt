package com.example.retrofitoff

import android.app.Application
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory.Companion.instance
import com.example.retrofitoff.mode2.RepositoriesUserItemClass
import com.example.retrofitoff.util.Constants.Companion.KEY_PUT_NAME
import com.example.retrofitoff.util.Constants.Companion.KEY_PUT_REPO
import kotlinx.coroutines.withContext
import kotlin.coroutines.coroutineContext

class IntentKey1: Application(){
    override fun onCreate() {
        super.onCreate()
        instance = this
    }
    companion object {
        lateinit var instance: IntentKey1
        private set
    }
}












