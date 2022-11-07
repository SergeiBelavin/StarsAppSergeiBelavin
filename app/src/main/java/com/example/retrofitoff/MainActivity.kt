package com.example.retrofitoff

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.retrofitoff.databinding.ActivityMainBinding
import com.example.retrofitoff.mode2.RepositoriesUserItem
import com.example.retrofitoff.repository.Repository


class MainActivity : AppCompatActivity(), AdapterReposNameReView.Listener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val adapter = AdapterReposNameReView(this)
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
        val IntentChartActivity =  ChartActivity.IntentChart.IntentChartActivity(this@MainActivity,list.owner.toString(), list.name.toString())
        startActivity(IntentChartActivity)

    }
}
