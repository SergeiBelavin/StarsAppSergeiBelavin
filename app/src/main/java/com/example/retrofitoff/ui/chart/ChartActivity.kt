package com.example.retrofitoff.ui.chart

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.retrofitoff.data.entity.constructor.ConstructorStar

import com.example.retrofitoff.data.repository.Repository
import com.example.retrofitoff.databinding.ChartActivityBinding
import com.example.retrofitoff.ui.subscribers.SubscribersActivity
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import java.util.*
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

    private lateinit var binding: ChartActivityBinding
    private lateinit var chartView: ChartViewModel

    lateinit var barChart: BarChart
    lateinit var barData: BarData
    lateinit var barDataSet: BarDataSet
    lateinit var barEntriesList: ArrayList<BarEntry>

    private var groupType = Repository.GroupType.FOURTEEN_DAYS
    private var dateResponseList = ArrayList<Map<Int, ConstructorStar>>()
    private var listForChart = ArrayList<Map<Int, ConstructorStar>>()
    private var dayRangeCalendar = ArrayList<Int>()
    private var getDayRangeCalendar = ArrayList<ArrayList<Int>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ChartActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repository = Repository()
        val viewModelFactory = ChartViewFactory(repository)
        val ownerName = intent.getSerializableExtra(KEY_NAME)
        val reposName = intent.getSerializableExtra(KEY_REPOS)
        chartView = ViewModelProvider(this, viewModelFactory)[ChartViewModel::class.java]



        barEntriesList = ArrayList()

        binding.fourteenDaysLong.setOnClickListener {
            clearData()
            groupType = Repository.GroupType.FOURTEEN_DAYS
            getReposStar(ownerName.toString(), reposName.toString())
        }
        binding.thirtyDaysLong.setOnClickListener {
            clearData()
            getReposStar(ownerName.toString(), reposName.toString())
            groupType = Repository.GroupType.THIRTY_DAYS
        }
        binding.sixtyDaysLong.setOnClickListener {
            clearData()
            groupType = Repository.GroupType.SIXTY_DAYS
            getReposStar(ownerName.toString(), reposName.toString())
        }
        chartView.chartResponse.observe(this) { dateList ->
            dateResponseList.addAll(dateList)
            barEntriesList = ArrayList()
            barChartData()
            barChart = binding.barChart
            barDataSet = BarDataSet(barEntriesList, "Количество звезд")
            barDataSet.valueTextColor = Color.BLACK
            barData = BarData(barDataSet)
            barChart.setFitBars(true)
            barChart.data = barData
            barChart.description.text = "Количество звезд по датам"
            barDataSet.valueTextSize = 16f
            barChart.animateY(2000)

        }

        binding.barChart.setOnClickListener {
            startSubActivity()
        }

    }

    private fun groupsType(groupType: Repository.GroupType): Int {
        return when (groupType) {
            Repository.GroupType.FOURTEEN_DAYS -> 14
            Repository.GroupType.THIRTY_DAYS -> 30
            Repository.GroupType.SIXTY_DAYS -> 60
        }
    }


    private fun barChartData() {
        val getGroupType = groupsType(groupType)
        for (i in 0 until getGroupType) {
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.DAY_OF_YEAR, -i)
            val dayAgo = calendar.time
            val uniqDatAgo = dayAgo.date + 31 * dayAgo.month * dayAgo.year * 1000
            dayRangeCalendar.add(uniqDatAgo)
            calendar.clear()
        }
        Log.d("DATE_LIST", "${dayRangeCalendar}")

        for (i in 0 until getGroupType) {
            getDayRangeCalendar.add(ArrayList(0))
        }

        for (i in 0 until dateResponseList.size) {
            if (dateResponseList[i].values.first().repo.neededForChart == 1) {
                listForChart.add(dateResponseList[i])
            }
        }

        dateResponseList.forEach {

            for (i in 0 until getDayRangeCalendar.size) {
                if (dayRangeCalendar[i] == it.keys.first()) {
                    getDayRangeCalendar[i].add(1)
                }
            }
        }

        for (i in 0 until getDayRangeCalendar.size) {
            Log.d("GET_DAY_RANGE3", "${getDayRangeCalendar}")
            barEntriesList.add(BarEntry(i.toFloat() + 1f, getDayRangeCalendar[i].size.toFloat()))
        }
    }

    fun clearData() {
        dateResponseList.clear()
        barEntriesList.clear()
        listForChart.clear()
        dayRangeCalendar.clear()
        getDayRangeCalendar.clear()
    }

    fun getReposStar(ownerName: String, reposName: String) {
        return chartView.getReposStars(ownerName, reposName, groupType)
    }
//gulihua10010

    fun startSubActivity() {
        val chartIntent = SubscribersActivity.createSubscribeIntent(this@ChartActivity, listForChart)
        startActivity(chartIntent)
    }

}
