package com.example.retrofitoff

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.retrofitoff.databinding.ActivityMainBinding
import com.example.retrofitoff.mode2.RepositoriesUserItemClass
import com.example.retrofitoff.repository.Repository
import com.example.retrofitoff.util.Constants.Companion.KEY_PUT_NAME
import com.example.retrofitoff.util.Constants.Companion.KEY_PUT_REPO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit


class MainActivity : AppCompatActivity(), AdapterReposNameReView.Listener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private var adapter = AdapterReposNameReView(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rcView.adapter = adapter

        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]

        binding.findName.setOnClickListener {



            val searchName = binding.addName.text.toString()
            viewModel.getPost(searchName)

            viewModel.myResponse.observe(this) {

RetrofitInstance.api2.


                /*responce ->
                responce.let { adapter.setList(it) }
                Log.d("MyLog", "${it}")
                Log.d("MyLog", "${responce}")
               // responce.let { adapter.setList() }

                 */
            }

        }


}

    override fun onClick(list: Call<List<RepositoriesUserItemClass?>?>) {
    //val putNameRepo = Intent(this@MainActivity, ChartRepoActivity::class.java).putExtra(KEY_PUT_REPO, list.body()?.name)
      //  .putExtra(KEY_PUT_NAME, list.body()?.owner?.login)
        //startActivity(putNameRepo)

        }

    }
