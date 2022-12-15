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
import com.example.retrofitoff.ui.chart.ChartActivity
import com.example.retrofitoff.ui.main.MainViewFactory
import com.example.retrofitoff.ui.main.RepoAdapter
import java.text.SimpleDateFormat
import java.util.*


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
        findView()


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

    private fun findView() {
        // findName = findViewById(R.id.findName)
        // addName = findViewById(R.id.addName)
        // rcView = findViewById(R.id.rcView)
        // progressBar = findViewById(R.id.progressBar)
    }
    fun testCal() {
        var minus = 2
        val currentDate = Calendar.getInstance()
        //currentDate.add(Calendar.MONTH, -minus)
        val sdf = SimpleDateFormat("yyyy/M/dd")
        Log.d("TEST_DATE1", "${currentDate.time.toString()}")
        currentDate.set(Calendar.DATE, 1)

        Log.d("TEST_DATE2", "${currentDate.time.toString()}")

        val cal = Calendar.getInstance().also {
            it.set(Calendar.DATE, 1)
            it.set(Calendar.MONTH, currentDate.time.month - minus)
            it.set(Calendar.YEAR, currentDate.time.year)
           // it.set(Calendar.DAY_OF_YEAR,1)
        }
        Log.d("TEST_DATE3", "${cal.time.toString()}")

    }
}

