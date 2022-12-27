package com.example.retrofitoff.data.ui.main

import Repository
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.example.retrofitoff.model.RepoUser
import com.example.retrofitoff.databinding.ActivityMainBinding
import com.example.retrofitoff.data.ui.chart.ChartActivity
import kotlinx.coroutines.launch
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.Calendar
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds


class MainActivity : MvpAppCompatActivity(), RepoAdapter.Listener, MainView {

    private val repository = Repository()
    private lateinit var binding: ActivityMainBinding
    private val adapter = RepoAdapter(this)
    private val moxyPresenter by moxyPresenter { MainPresenter(repository) }

    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rcView.adapter = adapter

        getRepoList()

        test()

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
        }
    }

    override fun startSending(boolean: Boolean) {
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
    fun test() {
        val calendar = Calendar.getInstance()
        var hours = calendar.time.hours
        var minutes = calendar.time.minutes
        var seconds = calendar.time.seconds

        val date = calendar.time

        date.minutes = date.minutes - date.minutes
        date.hours = date.hours - date.hours
        date.seconds = date.seconds - date.seconds

    }

}

