package com.example.retrofitoff

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModelProvider
import com.example.retrofitoff.databinding.ActivityMainBinding
import com.example.retrofitoff.mode2.RepositoriesUser
import com.example.retrofitoff.mode2.RepositoriesUserItem
import com.example.retrofitoff.repository.Repository
import com.example.retrofitoff.util.intentKey
import retrofit2.Call
import retrofit2.Response


class MainActivity : AppCompatActivity(), AdapterReposNameReView.Listener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainView
    private var adapter = AdapterReposNameReView(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rcView.adapter = adapter

        val repository = Repository()
        val viewModelFactory = MainViewFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[MainView::class.java]

        binding.findName.setOnClickListener {
            val searchName = binding.addName.text.toString()
            viewModel.getRepoList(searchName)
            viewModel.myResponse.observe(this) { responce ->
                responce.let {
                    adapter.setList(responce)
               //     Log.d ("MyLog", "$it")
                //    Log.d ("MyLog", "${responce}")
                }
            }
        }


    }

    override fun onClick(list: Call<RepositoriesUserItem>) {

        val intentMain = intentKey.IntentKey(this@MainActivity, "sergei", "windshiftgame")
        startActivity(intentMain)

       // val  putNameRepo = Intent(this@MainActivity, ChartActivity::class.java)
        //.putExtra(KEY_PUT_REPO, )
        //.putExtra(KEY_PUT_NAME, list)
       // startActivity(putNameRepo)

    }
}
