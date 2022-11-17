package com.example.retrofitoff.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.retrofitoff.databinding.ActivityMainBinding
import com.example.retrofitoff.mode.UserRepositoriesItem
import com.example.retrofitoff.data.repository.Repository
import com.example.retrofitoff.ui.chart.ChartActivity


class MainActivity : AppCompatActivity(), RepositoryAdapter.Listener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val adapter = RepositoryAdapter(this)
        binding.rcView.adapter = adapter

        val repository = Repository()
        val viewModelFactory = MainViewFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[MainView::class.java]

        binding.findName.setOnClickListener {
            val searchName = binding.addName.text.toString()
            viewModel.repoList(searchName)
            viewModel.myResponse.observe(this) { response ->
                response.let {
                    adapter.setList(it)
                }
            }
        }
    }
    override fun onClick(list: UserRepositoriesItem) {
        val intentChartActivity =  ChartActivity.createIntent(this@MainActivity,list.owner?.login.toString(), list.name.toString())
        startActivity(intentChartActivity)
    }
}
