package com.example.retrofitoff.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.retrofitoff.ReposAdapter
import com.example.retrofitoff.databinding.ActivityMainBinding
import com.example.retrofitoff.mode.RepositoriesUserItem
import com.example.retrofitoff.data.repository.Repository
import com.example.retrofitoff.ui.chart.ChartActivity


class MainActivity : AppCompatActivity(), ReposAdapter.Listener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val adapter = ReposAdapter(this)
        binding.rcView.adapter = adapter

        val repository = Repository()
        val viewModelFactory = MainViewFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[MainView::class.java]

        binding.findName.setOnClickListener {
            val searchName = binding.addName.text.toString()
            viewModel.getRepoList(searchName)
            viewModel.myResponse.observe(this) { responce ->
                responce.let {
                    adapter.setList(it)
                }
            }
        }
    }
    override fun onClick(list: RepositoriesUserItem) {
        val intentChartActivity =  ChartActivity.createIntent(this@MainActivity,list.owner?.login.toString(), list.name.toString())
        startActivity(intentChartActivity)
    }
}
