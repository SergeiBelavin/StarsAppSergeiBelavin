package com.example.retrofitoff.ui.chart

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.retrofitoff.data.entity.StarGroup
import com.example.retrofitoff.data.entity.constructor.ConstructorStar

import com.example.retrofitoff.data.repository.Repository
import com.example.retrofitoff.data.repository.UniqueDate
import com.example.retrofitoff.databinding.ChartActivityBinding
import com.example.retrofitoff.ui.chart.EnumRange.Companion.groupsType
import com.example.retrofitoff.ui.subscribers.SubscribersActivity
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

    private lateinit var binding: ChartActivityBinding
    private lateinit var chartView: ChartViewModel

    lateinit var barChart: BarChart
    lateinit var barData: BarData
    lateinit var barDataSet: BarDataSet
    lateinit var barEntriesList: ArrayList<BarEntry>

    private var groupType = EnumRange.Companion.GroupType.FOURTEEN_DAYS
    private var dateResponseList = emptyList<StarGroup>()
    private var dayRangeCalendar = ArrayList<Int>()
    private var getDayRangeCalendar = ArrayList<ArrayList<Int>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
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
            groupType = EnumRange.Companion.GroupType.FOURTEEN_DAYS
            clearData()
            getReposStar(ownerName.toString(), reposName.toString(), groupType)
        }
        binding.thirtyDaysLong.setOnClickListener {
            groupType = EnumRange.Companion.GroupType.THIRTY_DAYS
            clearData()
            getReposStar(ownerName.toString(), reposName.toString(), groupType)
        }
        binding.sixtyDaysLong.setOnClickListener {
            groupType = EnumRange.Companion.GroupType.SIXTY_DAYS
            clearData()
            getReposStar(ownerName.toString(), reposName.toString(), groupType)
        }
        chartView.chartResponse.observe(this) { dateList ->

            dateResponseList = dateList

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

    private fun barChartData() {
        val getGroupType = groupsType(groupType)

        dayRangeCalendar = UniqueDate().getUniqueArrayList(getGroupType)

        Log.d("DATE_LIST", "${dayRangeCalendar}")

        for (i in 0 until getGroupType) {
            getDayRangeCalendar.add(ArrayList())
        }

        dateResponseList.forEach {

            for (i in 0 until getDayRangeCalendar.size) {
                if (dayRangeCalendar[i] == it.uniqueDate) {
                    getDayRangeCalendar[i].add(1)
                }
            }
        }

        for (i in 0 until getDayRangeCalendar.size) {
            Log.d("GET_DAY_RANGE1", "${getDayRangeCalendar[0].size}")
            Log.d("GET_DAY_RANGE2", "${getDayRangeCalendar[1].size}")
            Log.d("GET_DAY_RANGE3", "${getDayRangeCalendar[2].size}")
            Log.d("GET_DAY_RANGE4", "${getDayRangeCalendar[3].size}")
            Log.d("GET_DAY_RANGE5", "${getDayRangeCalendar[4].size}")
            Log.d("GET_DAY_RANGE6", "${getDayRangeCalendar[5].size}")
            Log.d("GET_DAY_RANGE7", "${getDayRangeCalendar[6].size}")
            Log.d("GET_DAY_RANGE8", "${getDayRangeCalendar[7].size}")
            Log.d("GET_DAY_RANGE9", "${getDayRangeCalendar[8].size}")
            Log.d("GET_DAY_RANGE10", "${getDayRangeCalendar[9].size}")
            Log.d("GET_DAY_RANGE11", "${getDayRangeCalendar[10].size}")
            Log.d("GET_DAY_RANGE12", "${getDayRangeCalendar[11].size}")
            Log.d("GET_DAY_RANGE13", "${getDayRangeCalendar[12].size}")
            Log.d("GET_DAY_RANGE14", "${getDayRangeCalendar[13].size}")
            barEntriesList.add(BarEntry(i.toFloat() + 1f, getDayRangeCalendar[i].size.toFloat()))
        }
    }

    fun clearData() {
        dateResponseList = emptyList<StarGroup>()
        barEntriesList = ArrayList()
        dayRangeCalendar = ArrayList<Int>()
        Log.d("DAY_RANGE_DAY", "${barEntriesList.size}")
        getDayRangeCalendar = ArrayList<ArrayList<Int>>()
    }

    fun getReposStar(
        ownerName: String,
        reposName: String,
        groupType: EnumRange.Companion.GroupType,
    ) {
        return chartView.getReposStars(ownerName, reposName, groupType)
    }
//gulihua10010

    fun startSubActivity() {
        val chartIntent = SubscribersActivity.createSubscribeIntent(this@ChartActivity, dateResponseList)
        startActivity(chartIntent)
    }

}
