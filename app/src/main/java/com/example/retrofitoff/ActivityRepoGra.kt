package com.example.retrofitoff

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.retrofitoff.databinding.ActivityRepoGraBinding
import com.example.retrofitoff.repository.Repository


class ActivityRepoGra : AppCompatActivity() {
    private lateinit var binding: ActivityRepoGraBinding
    private lateinit var viewModel2: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRepoGraBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val item= intent.getSerializableExtra("RepoName")
        val item2= intent.getSerializableExtra("UserName")
        binding.repoName.text = item.toString()

        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel2 = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        viewModel2.getPosStat(item2.toString(), item.toString())
        viewModel2.myResponse2.observe(this, Observer {
            responce ->
            responce.body()?.let { Log.d("MeLogg", "$it")}
        })



    }
}