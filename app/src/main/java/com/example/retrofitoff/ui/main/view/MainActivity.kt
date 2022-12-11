package com.example.retrofitoff.ui.main.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitoff.R
import com.example.retrofitoff.model.RepoUser
import com.example.retrofitoff.databinding.ActivityMainBinding
import com.example.retrofitoff.data.repository.Repository
import com.example.retrofitoff.ui.chart.ChartActivity
import com.example.retrofitoff.ui.main.MainViewFactory
import com.example.retrofitoff.ui.main.RepoAdapter


class MainActivity : AppCompatActivity(), RepoAdapter.Listener, IMainView {

    private lateinit var findName: Button
    private lateinit var addName: EditText
    private lateinit var rcView: RecyclerView
    private lateinit var progressBar: ProgressBar

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private val repository = Repository()
    private val viewModelFactory = MainViewFactory(repository)
    private val adapter = RepoAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        super.onCreate(savedInstanceState)

        findView()

        setListener()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rcView.adapter = adapter

        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]

        binding.findName.setOnClickListener {

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
        repository.error.observe(this) { error ->
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
        }
    }

    private fun findView() {
        findName = findViewById(R.id.findName)
        addName = findViewById(R.id.addName)
        rcView = findViewById(R.id.rcView)
        progressBar = findViewById(R.id.progressBar)
    }

    private fun setListener() {
        findName.setOnClickListener {

        }
        rcView.setOnClickListener {

        }
    }

    override fun onFiendRepoUser(name: String) {
        TODO("Not yet implemented")
    }

    override fun getStarRepo(name: String, repo: String) {
        TODO("Not yet implemented")
    }

    override fun onShowProgress() {
        progressBar.visibility = View.VISIBLE
    }

    override fun onHideProgress() {
        progressBar.visibility = View.GONE
    }
}
