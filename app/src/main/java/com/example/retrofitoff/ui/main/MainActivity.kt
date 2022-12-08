package com.example.retrofitoff.ui.main

import android.os.Bundle
import android.util.Log
import android.util.Log.i
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.red
import androidx.lifecycle.ViewModelProvider
import com.example.retrofitoff.R
import com.example.retrofitoff.data.entity.RepoUser
import com.example.retrofitoff.data.entity.constructor.ConstructorRepo
import com.example.retrofitoff.databinding.ActivityMainBinding
import com.example.retrofitoff.data.repository.Repository
import com.example.retrofitoff.ui.chart.ChartActivity


class MainActivity : AppCompatActivity(), RepositoryAdapter.Listener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    // private var database = StarsRoomDatabase.getDb(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val adapter = RepositoryAdapter(this)
        binding.rcView.adapter = adapter
        val repository = Repository()
        val viewModelFactory = MainViewFactory(repository)

        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]

        binding.findName.setOnClickListener {

            if (binding.addName.text.isNotEmpty()) {
                viewModel.repoList(binding.addName.text.toString())
                repository.error.observe(this) {
                    errorInternet ->
                    Toast.makeText(this, errorInternet, Toast.LENGTH_SHORT).show()
                }

                viewModel.error.observe(this) {
                    error ->
                    Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
                }
                viewModel.myResponse.observe(this) { response ->
                    Log.d("MainViewAdapter", "$response")

                    response.let {
                        adapter.setList(response!!)
                    }
                }
            } else {
                binding.addName.error = "Enter a name"
                binding.addName.hint = "Enter a name"
            }
        }
    }

    override fun onClick(list: RepoUser) {
        val chartIntent = ChartActivity.createIntent(this@MainActivity, list.user.name, list.name)
        startActivity(chartIntent)
    }
}
