package com.example.retrofitoff

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat.startActivity

import com.example.retrofitoff.util.Constants
import kotlinx.coroutines.withContext

object InitPut {
    fun isIntentAvailable(context: Context, action: String): Boolean {
        val packageManager = context.packageManager
        val intent = Intent(action)
        val list = packageManager.queryIntentActivities(intent,
            PackageManager.MATCH_DEFAULT_ONLY)
        return list.size > 0
}
}
