package com.example.retrofitoff.ui.sub

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import com.example.retrofitoff.data.entity.ChartListItem
import com.example.retrofitoff.model.StarGroup
import com.example.retrofitoff.databinding.SubscribersActivityBinding
import java.io.Serializable

class SubscribersActivity : AppCompatActivity() {



    companion object{

        private val LOG_ACTIVITY = "SUB_ACTIVITY"
        private val KEY_NAME = "KeyNAME"

        fun createSubscribeIntent(context: Context, list: List<ChartListItem>): Intent {
            return Intent(context, SubscribersActivity::class.java)
                .putExtra(KEY_NAME, list as Serializable)
        }
    }

    private lateinit var binding: SubscribersActivityBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        super.onCreate(savedInstanceState)
        binding = SubscribersActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val adapter = SubscribersAdapter()
        binding.subList.adapter = adapter

        val avatarList = intent.getSerializableExtra(KEY_NAME) as List<ChartListItem>
        Log.d("$LOG_ACTIVITY + AVATAR", "${avatarList[0].avatarUrl.toString()}")
        adapter.setList(avatarList)

    }

}