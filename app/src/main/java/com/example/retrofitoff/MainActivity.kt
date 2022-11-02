package com.example.retrofitoff

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.retrofitoff.databinding.ActivityMainBinding
import com.example.retrofitoff.mode2.ReposUserItem
import com.example.retrofitoff.repository.Repository
import okhttp3.logging.HttpLoggingInterceptor


class MainActivity : AppCompatActivity(), AdapterReposNameReView.Listener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    private var adapter = AdapterReposNameReView(this)

    private var KEY_PUT_NAME = "UserName"
    private var KEY_PUT_REPO = "RepoName"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rcView.adapter = adapter

        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)


        binding.findName.setOnClickListener {
            val searchName = binding.addName.text.toString()
            viewModel.getPost(searchName)

            viewModel.myResponse.observe(this) { response ->

                if(BuildConfig.DEBUG){

                    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
                    response.body()?.let { adapter.setList(it) }
                }else{
                    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

                }
            }
        }


}


    override fun onClick(list: ReposUserItem) {
    val putNameRepo = Intent(this@MainActivity, ChartRepoActivity::class.java).putExtra(/* name = */ KEY_PUT_REPO, /* value = */ list.name)
        .putExtra(KEY_PUT_NAME, list.owner.login)
        startActivity(putNameRepo)
        }

    }
