package com.example.retrofitoff.ui.main.view

import android.os.Build.VERSION_CODES.M
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.retrofitoff.model.RepoUser
import com.example.retrofitoff.databinding.ActivityMainBinding
import com.example.retrofitoff.data.repository.Repository
import com.example.retrofitoff.data.repository.UniqueDate
import com.example.retrofitoff.model.StarGroup
import com.example.retrofitoff.ui.chart.ChartActivity
import com.example.retrofitoff.ui.main.MainViewFactory
import com.example.retrofitoff.ui.main.RepoAdapter
import java.text.SimpleDateFormat
import java.time.YearMonth
import java.util.*
import kotlin.collections.ArrayList
import kotlin.time.days


class MainActivity : AppCompatActivity(), RepoAdapter.Listener {

    //private lateinit var findName: Button
    //private lateinit var addName: EditText
    //private lateinit var rcView: RecyclerView
    //private lateinit var progressBar: ProgressBar


    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private val repository = Repository()
    private val viewModelFactory = MainViewFactory(repository)
    private val adapter = RepoAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        super.onCreate(savedInstanceState)

        testCal()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rcView.adapter = adapter

        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]

        binding.findName.setOnClickListener {

            if (binding.addName.text.isNotEmpty()) {
                binding.addName.hint = "Find a user"
                viewModel.repoList(binding.addName.text.toString())

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
        errorToast()
    }

    override fun onClick(list: RepoUser) {
        val chartIntent = ChartActivity.createIntent(this@MainActivity, list.user.name, list.name)
        startActivity(chartIntent)
    }

    private fun errorToast() {
        viewModel.error.observe(this) { error ->
            binding.errorText.text = error
            if (error != "") {
                binding.cardViewError.visibility = View.VISIBLE
            }
            binding.errorOk.setOnClickListener {
                binding.cardViewError.visibility = View.GONE
            }
            //Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
        }
        viewModel.error.value = ""
    }

    fun testCal() {
        val listTest = ArrayList<Int>()
        val calWeek = Calendar.getInstance()
        var date = 1 * 7
        calWeek.add(Calendar.DAY_OF_YEAR, -date)
        Log.d("TEST_WEEK1", "${calWeek.time}")

        calWeek.add(Calendar.DAY_OF_YEAR, -calWeek.time.day + 1)
        Log.d("TEST_WEEK2", "${calWeek.time}")
        var num = 0

        for (i in 0..6) {
            calWeek.add(Calendar.DAY_OF_YEAR, +1)
            val dateInt = calWeek.time
            listTest.add(UniqueDate().getUniqueDate(dateInt))
        }
        Log.d("TEST_WEEK3", "${calWeek.time}")
    }

    fun paging(list: ArrayList<StarGroup>,  ) {


    }
}

