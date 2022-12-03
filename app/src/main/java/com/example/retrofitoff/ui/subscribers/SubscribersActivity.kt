package com.example.retrofitoff.ui.subscribers

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.retrofitoff.data.entity.constructor.ConstructorStar
import com.example.retrofitoff.databinding.SubscribersActivityBinding
import java.io.Serializable

class SubscribersActivity : AppCompatActivity() {



    companion object{

        private val LOG_ACTIVITY = "SUB_ACTIVITY"

        private val KEY_NAME = "KeyNAME"
        private val KEY_AVATAR = "KeyAVATAR"

        fun createSubscribeIntent(context: Context, avatarUsers: List<String>, nameUsers: List<String>,): Intent {
            return Intent(context,SubscribersActivity::class.java)
                .putExtra(KEY_NAME, avatarUsers as Serializable)
                .putExtra(KEY_AVATAR, nameUsers as Serializable)
        }
    }

    private lateinit var binding: SubscribersActivityBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SubscribersActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val adapter = SubscribersAdapter()
        binding.subList.adapter = adapter

        var avatarList = intent.getSerializableExtra(KEY_AVATAR)
        Log.d("$LOG_ACTIVITY + AVATAR", "${avatarList.toString()}")
        var nameList = intent.getSerializableExtra(KEY_NAME)
        Log.d("$LOG_ACTIVITY + NAME", "${nameList.toString()}")

    }

}