package com.example.retrofitoff.ui.chart

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build.VERSION_CODES.P
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.core.graphics.drawable.toDrawable
import androidx.lifecycle.ViewModelProvider
import com.example.retrofitoff.R
import com.example.retrofitoff.data.repository.DateConverter
import com.example.retrofitoff.model.StarGroup

import com.example.retrofitoff.data.repository.Repository
import com.example.retrofitoff.data.repository.UniqueDate
import com.example.retrofitoff.databinding.ChartActivityBinding
import com.example.retrofitoff.ui.chart.EnumRange.Companion.groupsType
import com.example.retrofitoff.ui.subscribers.SubscribersActivity
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import java.text.SimpleDateFormat
import java.time.Month
import java.util.*
import java.util.Calendar.DAY_OF_YEAR
import kotlin.collections.ArrayList

class ChartActivity : AppCompatActivity() {

    companion object {

        private val KEY_NAME = "UserName"
        private val KEY_REPOS = "RepoName"

        fun createIntent(context: Context, name: String, repo: String): Intent {
            return Intent(context, ChartActivity::class.java).putExtra(KEY_NAME, name)
                .putExtra(KEY_REPOS, repo)
        }

    }

    private lateinit var binding: ChartActivityBinding
    private lateinit var chartView: ChartViewModel

    lateinit var barChart: BarChart
    lateinit var barData: BarData
    lateinit var barDataSet: BarDataSet
    lateinit var barEntriesList: ArrayList<BarEntry>

    private var groupType = EnumRange.Companion.GroupType.WEEK
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

        error(repository)

        barEntriesList = ArrayList()
        val weekBack = 7
        val calendar = Calendar.getInstance()
        val parser = SimpleDateFormat("dd/MM/yyyy")
        var numWeek = 0
        var numMonth = 0


        var clickOrNotSelectedDate = false
        val dateList = listOf("week", "month", "year")
        var numDateList = 0

        binding.weekNext.setOnClickListener {
            Log.d("DATE_MINUS", "${numWeek}")

            if (!clickOrNotSelectedDate) {
                if (numDateList > 2) {
                    numDateList = 0
                }
                if (numDateList < 0) {
                    numDateList = 2
                }
                binding.selectedDate.text = dateList[numDateList]
                numDateList++
            }

            if (clickOrNotSelectedDate) {

                binding.weekNext.setBackgroundColor(ContextCompat.getColor(this, R.color.grey))

                if (numWeek < 0) {
                    binding.weekNext.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
                    numWeek++
                    calendar.add(Calendar.DATE, +weekBack)
                    binding.selectedDate.text = "${parser.format(Date(calendar.timeInMillis))}"
                } else {
                    binding.weekNext.setBackgroundColor(ContextCompat.getColor(this, R.color.grey))
                }

                if (numWeek == 0) {
                    binding.weekNext.setBackgroundColor(ContextCompat.getColor(this, R.color.grey))
                    binding.selectedDate.text = "current week"
                }

            }
        }
        binding.weekBack.setOnClickListener {
            if (!clickOrNotSelectedDate) {

                if (numDateList < 0) {
                    numDateList = 2
                }
                if (numDateList > 2) {
                    numDateList = 1
                }
                binding.selectedDate.text = dateList[numDateList]
                numDateList--

            }

            if (clickOrNotSelectedDate) {
                binding.weekBack.setOnClickListener {
                    numWeek--
                    calendar.add(Calendar.DATE, -weekBack)
                    binding.selectedDate.text = "${parser.format(Date(calendar.timeInMillis))}"
                }
            }

        }

        binding.showGraph.setOnClickListener {
            if (!clickOrNotSelectedDate) {
                clickOrNotSelectedDate = true
            }
            if (clickOrNotSelectedDate) {
                clearData()
                groupType = EnumRange.Companion.GroupType.MONTH
                getReposStar(ownerName.toString(), reposName.toString(), groupType)
            }
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

        if (getGroupType == 60) {

            for (i in 0 until dayRangeCalendar.size) {
                getDayRangeCalendar.add(ArrayList())
            }

            dateResponseList.forEach {

                for (i in 0 until getDayRangeCalendar.size) {
                    if (it.uniqueDate!! <= dayRangeCalendar[i] && it.uniqueDate!! >= dayRangeCalendar[i + 1]) {
                        getDayRangeCalendar[i].add(1)
                    }
                }
            }

            for (i in 0 until getDayRangeCalendar.size) {
                barEntriesList.add(BarEntry(i.toFloat() + 1f,
                    getDayRangeCalendar[i].size.toFloat()))
            }
        } else {

            Log.d("DATE_LIST", "${dayRangeCalendar}")
            Log.d("DATE_LIST_RESP", "${dateResponseList}")

            for (i in 0 until dayRangeCalendar.size) {
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

                barEntriesList.add(BarEntry(i.toFloat() + 1f,
                    getDayRangeCalendar[i].size.toFloat()))
            }
        }
    }

    fun clearData() {
        dateResponseList = emptyList<StarGroup>()
        barEntriesList = ArrayList()
        dayRangeCalendar = ArrayList<Int>()
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
        val chartIntent =
            SubscribersActivity.createSubscribeIntent(this@ChartActivity, dateResponseList)
        startActivity(chartIntent)
    }

    fun error(repository: Repository) {

        //repository.error.observe(this) { error ->
        //    Toast.makeText(this, error, Toast.LENGTH_SHORT).show()

    }
}