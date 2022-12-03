package com.example.retrofitoff.ui.subscribers

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.retrofitoff.data.entity.constructor.ConstructorStar
import com.example.retrofitoff.databinding.SubscribersActivityBinding
import java.io.Serializable

class SubscribersActivity : AppCompatActivity() {



    companion object{

        private val KEY_NAME = "KeyList"
        private val KEY_AVATAR = "KeyList"

        fun createSubscribeIntent(context: Context, avatarUsers: List<String>, nameUsers: List<String>,): Intent {
            return Intent(context,SubscribersActivity::class.java)
                .putExtra(KEY_NAME, avatarUsers as Serializable)
                .putExtra(KEY_AVATAR, nameUsers as Serializable)
        }
    }

    private lateinit var binding: SubscribersActivityBinding
    private var subList = ArrayList<Map<Int, ConstructorStar>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SubscribersActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val adapter = SubscribersAdapter()
        binding.subList.adapter = adapter

        //var avatarList = intent.getSerializableExtra(KEY_AVATAR)
        //var nameList = intent.getSerializableExtra(KEY_NAME)

    }

}