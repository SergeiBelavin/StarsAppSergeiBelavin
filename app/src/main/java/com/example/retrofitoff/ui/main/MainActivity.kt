package com.example.retrofitoff.ui.main

import Repository
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.example.retrofitoff.R
import com.example.retrofitoff.model.RepoUser
import com.example.retrofitoff.databinding.ActivityMainBinding
import com.example.retrofitoff.ui.chart.ChartActivity
import kotlinx.coroutines.launch
import kotlinx.serialization.json.JsonNull.boolean
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter


class MainActivity : MvpAppCompatActivity(), RepoAdapter.Listener, MainView {

    private val repository = Repository
    private lateinit var binding: ActivityMainBinding
    private val adapter = RepoAdapter(this)
    private val moxyPresenter by moxyPresenter { MainPresenter(repository) }

    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rcView.adapter = adapter
        getRepoList()

    }

    override fun onClickAdapter(list: RepoUser) {
        val chartIntent = ChartActivity.createIntent(this@MainActivity, list.user.name, list.name)
        startActivity(chartIntent)
    }

    override fun showError(message: String) {
        binding.errorText.text = message
        binding.cardViewError.visibility = View.VISIBLE
        binding.errorOk.setOnClickListener {
            binding.cardViewError.visibility = View.GONE
            unlockedClick(true)
        }
    }

    override fun unlockedClick(boolean: Boolean) {
        binding.progressBar.isInvisible = boolean
        binding.rcView.isVisible = boolean
        binding.addName.isEnabled = boolean
        binding.findName.isEnabled = boolean
    }

    fun getRepoList() {

        binding.findName.setOnClickListener {

            if (binding.addName.text.isNotEmpty()) {
                binding.addName.hint = "Find a user"

                lifecycleScope.launch {
                    adapter.setList(moxyPresenter.getRepoList(binding.addName.text.toString()))
                }

            } else {
                binding.addName.error = "Enter a name"
                binding.addName.hint = "Enter a name"
            }
        }

    }

    override fun getNameRepo(name: String) {

    }

}

