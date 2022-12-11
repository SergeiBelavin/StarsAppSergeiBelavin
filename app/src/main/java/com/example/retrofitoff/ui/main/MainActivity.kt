package com.example.retrofitoff.ui.main

import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.retrofitoff.model.RepoUser
import com.example.retrofitoff.databinding.ActivityMainBinding
import com.example.retrofitoff.data.repository.Repository
import com.example.retrofitoff.ui.chart.ChartActivity
import java.time.LocalDateTime
import java.time.Month
import java.util.Calendar
import kotlin.time.Duration.Companion.days
import kotlin.time.days


class MainActivity : AppCompatActivity(), RepositoryAdapter.Listener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private val repository = Repository()
    private val viewModelFactory = MainViewFactory(repository)
    private val adapter = RepositoryAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rcView.adapter = adapter

        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]

        binding.findName.setOnClickListener {
            val calen = Calendar.getInstance()
            val days =calen.get(Calendar.DAY_OF_YEAR)
            Log.d("MainViewAdapter111", "$calen")

            val calen1 = Calendar.getInstance()
            calen1.get(Calendar.DAY_OF_MONTH)
            Log.d("MainViewAdapter222", "$calen1")

            val calen2 = Calendar.getInstance()
            calen2.get(Calendar.DAY_OF_MONTH)
            Log.d("MainViewAdapter333", "$calen2")

            if (binding.addName.text.isNotEmpty()) {
                viewModel.repoList(binding.addName.text.toString())

                error()

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
    private fun error() {
        repository.error.observe(this) {
                error ->
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
        }
    }
}
