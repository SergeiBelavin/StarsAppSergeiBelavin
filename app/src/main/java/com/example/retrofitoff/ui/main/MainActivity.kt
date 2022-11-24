package com.example.retrofitoff.ui.main

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.retrofitoff.data.entity.RepoUser
import com.example.retrofitoff.databinding.ActivityMainBinding
import com.example.retrofitoff.data.repository.Repository
import com.example.retrofitoff.ui.chart.ChartActivity


class MainActivity : AppCompatActivity(), RepositoryAdapter.Listener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val adapter = RepositoryAdapter(this)
        binding.rcView.adapter = adapter

        val repository = Repository()

        val viewModelFactory = MainViewFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]

        binding.findName.setOnClickListener {
            val searchName = binding.addName.text.toString()

            viewModel.repoList(searchName)
            viewModel.myResponse.observe(this) { response ->
                Log.d("MainViewAdapter", "$response")

                response.let {
                    adapter.setList(response!!)
                }
            }
        }
    }

    override fun onClick(list: RepoUser) {
        val chartIntent = ChartActivity.createIntent(this@MainActivity, list.user.name, list.name)

        startActivity(chartIntent)
    }
}
