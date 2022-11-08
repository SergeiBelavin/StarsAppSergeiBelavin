package com.example.retrofitoff

import android.R
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.retrofitoff.databinding.ActivityChartRepoBinding
import com.example.retrofitoff.repository.Repository
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import java.util.*
import kotlin.collections.ArrayList


class ChartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChartRepoBinding
    private lateinit var chartView: ChartView
    lateinit var barChart: BarChart
    lateinit var barData: BarData
    lateinit var barDataSet: BarDataSet
    lateinit var barEntriesList: ArrayList<BarEntry>


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChartRepoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = intent.getSerializableExtra("UserName")
        val repos = intent.getSerializableExtra("RepoName")
       // binding.test.text = name.toString()

        val repository = Repository()
        val viewModelFactory = CharViewFactory(repository)

        chartView = ViewModelProvider(this, viewModelFactory)[ChartView::class.java]
        chartView.getReposStars(name.toString(), repos.toString())
        chartView.chartResponseEmitter.observe(this) { dateList ->
        Log.d("MyLog", "$dateList")

        }

            barChart = binding.barChart

        getBarChartData()

        barDataSet = BarDataSet(barEntriesList, "Количество звезд по датам")
        barData = BarData(barDataSet)
        barChart.data = barData
        barDataSet.valueTextColor = Color.RED
        barDataSet.color = R.color.holo_purple
        barDataSet.valueTextSize = 18f
        barChart.description.isEnabled = false


        barChart.setOnClickListener{
            val i = Intent(this@ChartActivity, MainActivity::class.java)
            startActivity(i)
        }

    }

    private fun getBarChartData() {

        barEntriesList = ArrayList()
        barEntriesList.add(BarEntry(1f, 1f))
        barEntriesList.add(BarEntry(2f, 2f))
        barEntriesList.add(BarEntry(3f, 3f))
        barEntriesList.add(BarEntry(4f, 4f))
        barEntriesList.add(BarEntry(5f, 5f))
        barEntriesList.add(BarEntry(6f, 6f))
        barEntriesList.add(BarEntry(7f,7f))
        barEntriesList.add(BarEntry(8f,8f))
        barEntriesList.add(BarEntry(9f,9f))
        barEntriesList.add(BarEntry(10f,10f))
        barEntriesList.add(BarEntry(11f,11f))
        barEntriesList.add(BarEntry(12f, 12f))
        barEntriesList.add(BarEntry(13f,13f))
        barEntriesList.add(BarEntry(14f,15f))

    }

    interface IntentChart {

    companion object {

        val KEY_NAME = "UserName"
        val KEY_REPOS = "RepoName"

        fun intentChartActivity(context: Context, name: String, repo: String): Intent {
            return Intent(context, ChartActivity::class.java)
                .putExtra(KEY_NAME, name)
                .putExtra(KEY_REPOS, repo)
        }
        }
    }
}