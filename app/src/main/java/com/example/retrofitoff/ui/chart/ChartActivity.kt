package com.example.retrofitoff.ui.chart

import android.R
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.retrofitoff.ChartView
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
    private lateinit var chartView: ChartView
    lateinit var barChart: BarChart
    lateinit var barData: BarData
    lateinit var barDataSet: BarDataSet
    lateinit var barEntriesList: ArrayList<BarEntry>
    lateinit var dateResponseList: ArrayList<Long>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChartRepoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getBarChartData(14)

        val repository = Repository()
        val viewModelFactory = CharViewFactory(repository)

        chartView = ViewModelProvider(this, viewModelFactory)[ChartView::class.java]


        val name = intent.getSerializableExtra(KEY_NAME)
        val repos = intent.getSerializableExtra(KEY_REPOS)
        chartView.getReposStars(name.toString(), repos.toString(),)
        chartView.chartResponseEmitter.observe(this) { dateList ->
            Log.d("MyLog", "$dateList")
            dateResponseList = dateList as ArrayList<Long>
            Log.d("MyLog1", "$dateResponseList")
        }

        barChart = binding.barChart

        barDataSet = BarDataSet(barEntriesList, "Количество звезд по датам")
        barData = BarData(barDataSet)
        barChart.data = barData
        barDataSet.valueTextColor = Color.RED
        barDataSet.color = R.color.holo_purple
        barDataSet.valueTextSize = 0f
        barChart.description.isEnabled = true


        barChart.setOnClickListener {
            val i = Intent(this@ChartActivity, MainActivity::class.java)
            startActivity(i)
        }

        binding.sixtyDaysLong.setOnClickListener {

            getBarChartData(60)
        }
        binding.thirtyDaysLong.setOnClickListener {

            getBarChartData(30)
        }
        binding.fourteenDaysLong.setOnClickListener {

            getBarChartData(14)
        }

    }

     fun getBarChartData(range: Int) {
         barEntriesList = ArrayList()

         for (i in 0..range){
             barEntriesList.add(BarEntry(i.toFloat(),2f))
         }
    }

    fun RangeSelector() {

    }

}