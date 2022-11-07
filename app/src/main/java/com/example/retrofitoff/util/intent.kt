package com.example.retrofitoff.util

import android.content.Context
import android.content.Intent
import android.os.Parcelable
import android.provider.Contacts
import android.provider.Contacts.SettingsColumns.KEY
import com.example.retrofitoff.ChartActivity
import com.example.retrofitoff.util.Constants.Companion.KEY_NAME

import com.example.retrofitoff.util.Constants.Companion.KEY_REPOS

class intentKey {
    companion object {
        fun IntentKey(context: Context, name: String, repo: String): Intent {
            val intentNameAndRepos = Intent(context, ChartActivity::class.java)
            intentNameAndRepos.putExtra(KEY_NAME, name)
            intentNameAndRepos.putExtra(KEY_REPOS, repo)
            return intentNameAndRepos
        }

    }
}