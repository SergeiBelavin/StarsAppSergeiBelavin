package com.example.retrofitoff
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitoff.databinding.ActivityMainBinding
import com.example.retrofitoff.modelReposUser.PostReposItem
import com.example.retrofitoff.repository.Repository



class MainActivity : AppCompatActivity(), AdapterReposNameReView.Listener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private var adapter = AdapterReposNameReView(this)
    lateinit var recyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rcView.layoutManager = LinearLayoutManager(this)
        recyclerView = binding.rcView
        recyclerView.adapter = adapter

        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        binding.findName.setOnClickListener {
            val searchName = binding.addName.text.toString()
            viewModel.getPost(searchName)
            viewModel.myResponse.observe(this, Observer { response ->
                response.body()?.let { adapter.setList(it) }
            })
        }
}

    override fun onClick(list: PostReposItem) {
    val putNameRepo = Intent(this@MainActivity, ActivityRepoGra::class.java)
        putNameRepo.putExtra("RepoName", list.name)
        putNameRepo.putExtra("UserName", list.owner.login)
        startActivity(putNameRepo)
        }
    }
