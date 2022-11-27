package com.example.retrofitoff.ui.chart

import android.R
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.retrofitoff.ChartViewModel
import com.example.retrofitoff.data.entity.RepoUser
import com.example.retrofitoff.data.entity.StarGroup

import com.example.retrofitoff.ui.main.MainActivity
import com.example.retrofitoff.databinding.ActivityChartRepoBinding
import com.example.retrofitoff.data.repository.Repository
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import kotlin.collections.ArrayList

class ChartActivity : AppCompatActivity() {

    companion object {

        private val KEY_NAME = "UserName"
        private val KEY_REPOS = "RepoName"

        fun createIntent(context: Context, name: String, repo: String): Intent {
            return Intent(context, ChartActivity::class.java)
                .putExtra(KEY_NAME, name)
                .putExtra(KEY_REPOS, repo)
        }

    }

    private lateinit var binding: ActivityChartRepoBinding
    private lateinit var chartView: ChartViewModel


    lateinit var barChart: BarChart
    lateinit var barData: BarData
    lateinit var barDataSet: BarDataSet
    lateinit var barEntriesList: ArrayList<BarEntry>
    lateinit var dateResponseList: ArrayList<List<List<StarGroup>>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChartRepoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repository = Repository()
        val viewModelFactory = ChartViewFactory(repository)

        chartView = ViewModelProvider(this, viewModelFactory)[ChartViewModel::class.java]

        val ownerName = intent.getSerializableExtra(KEY_NAME)
        val reposName = intent.getSerializableExtra(KEY_REPOS)
        dateResponseList = ArrayList<List<List<StarGroup>>>()
        chartView.getReposStars(ownerName.toString(), reposName.toString())
        chartView.chartResponse.observe(this) { dateList ->
            dateResponseList.add(dateList)

            Log.d("!ChartDateResponseList", "${dateResponseList.size}")
            Log.d("!ChartDateResponseList", "${dateResponseList}")
            Log.d("!ChartDateResponseList", "${dateResponseList[0][1].size}")

            barEntriesList = ArrayList()

            barChartData()

            barChart = binding.barChart
            barDataSet = BarDataSet(barEntriesList, "Количество звезд по датам")
            barData = BarData(barDataSet)
            barChart.data = barData
            barDataSet.valueTextColor = Color.RED
            barDataSet.color = R.color.holo_purple
            barDataSet.valueTextSize = 0f
            barChart.description.isEnabled = true
        }


        //barChart.setOnClickListener {
        //    val i = Intent(this@ChartActivity, MainActivity::class.java)
        //    startActivity(i)
        //}
    }

    private fun barChartData() {

        for (i in 0 until 14) {
            var sizeResponse = dateResponseList[0][i].size.toFloat()
            val oneNum = 1
            if (sizeResponse.toInt() == 0) {sizeResponse == oneNum.toFloat()}
            Log.d("SIZE_RESPONSE_LIST", "$sizeResponse")
            Log.d("RESPONSE_LIST_CHAR", "$dateResponseList")
            Log.d("!ChartDateResponseList", "${dateResponseList[0][1].size}")
            barEntriesList.add(BarEntry(i.toFloat(), sizeResponse))
        }

        // gulihua10010
    }
}
