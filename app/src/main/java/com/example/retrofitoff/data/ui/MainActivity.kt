package com.example.retrofitoff.data.ui

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.retrofitoff.model.RepoUser
import com.example.retrofitoff.databinding.ActivityMainBinding
import com.example.retrofitoff.data.repository.Repository
import com.example.retrofitoff.mvp.RepoAdapter
import com.example.retrofitoff.mvp.activity.ChartActivity


class MainActivity : AppCompatActivity(), RepoAdapter.Listener, MainView {



    //Presenter setup
    @InjectPresenter
    lateinit var mainPresenter: MainPresenter

    private val repository = Repository()

    @ProvidePresenter
    fun provideMainPresenter(): MainPresenter {
        return MainPresenter(mainRepository = repository)
    }



    private lateinit var binding: ActivityMainBinding
    private val adapter = RepoAdapter(this)

    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        super.onCreate(savedInstanceState)
        //mainPresenter.attachView(this@MainActivity)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rcView.adapter = adapter

        // viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]

        binding.findName.setOnClickListener {

            if (binding.addName.text.isNotEmpty()) {
                binding.addName.hint = "Find a user"
                provideMainPresenter().getRepoList(binding.addName.text.toString())

            } else {
                binding.addName.error = "Enter a name"
                binding.addName.hint = "Enter a name"
            }
        }

    }

    @Override
    override fun onClickAdapter(list: RepoUser) {
        val chartIntent = ChartActivity.createIntent(this@MainActivity, list.user.name, list.name)
        startActivity(chartIntent)
    }

    @Override
    override fun showError(message: String) {

        binding.errorText.text = message
        binding.cardViewError.visibility = View.VISIBLE

        binding.errorOk.setOnClickListener {
            binding.cardViewError.visibility = View.GONE
        }
    }

    @Override
    override fun startSending() {
        binding.progressBar.visibility = View.VISIBLE
        binding.rcView.visibility = View.GONE
        binding.addName.isEnabled = true
        binding.findName.isEnabled = true
    }

    @Override
    override fun endSending() {
        binding.progressBar.visibility = View.GONE
        binding.rcView.visibility = View.VISIBLE
        binding.addName.isEnabled = false
        binding.findName.isEnabled = false
    }
}

