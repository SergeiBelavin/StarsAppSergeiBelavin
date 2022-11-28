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
    lateinit var groupType: Repository.GroupType


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChartRepoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repository = Repository()
        val viewModelFactory = ChartViewFactory(repository)
        groupType = Repository.GroupType.FOURTEEN_DAYS

        chartView = ViewModelProvider(this, viewModelFactory)[ChartViewModel::class.java]

        val ownerName = intent.getSerializableExtra(KEY_NAME).toString()
        val reposName = intent.getSerializableExtra(KEY_REPOS).toString()

        binding.sixtyDaysLong.setOnClickListener {
            groupType = Repository.GroupType.SIXTY_DAYS
           // getRepStar(ownerName, reposName,)
            chartView.getReposStars(ownerName, reposName, groupType)
            barChartData()
        }
        binding.fourteenDaysLong.setOnClickListener {
            groupType = Repository.GroupType.FOURTEEN_DAYS
           // getRepStar(ownerName, reposName,)
            chartView.getReposStars(ownerName, reposName, groupType)
            barChartData()
        }
        binding.thirtyDaysLong.setOnClickListener {
            groupType = Repository.GroupType.THIRTY_DAYS
           // getRepStar(ownerName, reposName,)
            chartView.getReposStars(ownerName, reposName, groupType)
            barChartData()
        }

        dateResponseList = ArrayList()

        chartView.chartResponse.observe(this) { dateList ->
            dateResponseList.add(dateList)

            barEntriesList = ArrayList()
            //clickEnumSelect(ownerName, reposName,)
            barChartEmpty()

            barChart = binding.barChart
            barDataSet = BarDataSet(barEntriesList, "Количество звезд по датам")
            barData = BarData(barDataSet)
            barChart.data = barData
            barDataSet.valueTextColor = Color.RED
            barDataSet.color = R.color.holo_purple
            barDataSet.valueTextSize = 0f
            barChart.description.isEnabled = true
        }


    }

    private fun barChartData() {

        for (i in 0..13) {
            val sizeResponse = dateResponseList[0][i].size.toFloat()
            val oneNum = 1
            val n = 0
            if (sizeResponse == n.toFloat()) {
                sizeResponse == oneNum.toFloat()
            }
            Log.d("SIZE_RESPONSE_LIST", "$sizeResponse")
            Log.d("RESPONSE_LIST_CHAR", "$dateResponseList")
            Log.d("DATE_RESP_LIST_0_1.SIZE", "${dateResponseList[0][1].size}")
            barEntriesList.add(BarEntry(i.toFloat(), sizeResponse))
        }
        // gulihua10010
    }
    private fun barChartEmpty() {
        barEntriesList.add(BarEntry(1f,1f))
    }
    /*private fun clickEnumSelect(ownerName:String, reposName:String) {
        binding.sixtyDaysLong.setOnClickListener {
            groupType = Repository.GroupType.SIXTY_DAYS
            getRepStar(ownerName, reposName,)
        }
        binding.fourteenDaysLong.setOnClickListener {
            groupType = Repository.GroupType.FOURTEEN_DAYS
            getRepStar(ownerName, reposName,)
        }
        binding.thirtyDaysLong.setOnClickListener {
            groupType = Repository.GroupType.THIRTY_DAYS
            getRepStar(ownerName, reposName,)
        }
    }


    private fun getRepStar(ownerName: String, reposName: String,){
        chartView.getReposStars(ownerName, reposName, groupType)
        barChartData()
    }

     */
}
