package com.example.retrofitoff.data.ui.chart

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.retrofitoff.data.repository.DateConverter
import com.example.retrofitoff.model.StarGroup

import com.example.retrofitoff.data.repository.Repository
import com.example.retrofitoff.data.repository.UniqueDate
import com.example.retrofitoff.databinding.ChartActivityBinding
import com.example.retrofitoff.data.ui.main.EnumRange
import com.example.retrofitoff.data.ui.main.EnumRange.Companion.groupsType
import com.example.retrofitoff.data.ui.sub.SubscribersActivity
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter
import java.util.*
import kotlin.collections.ArrayList

@Suppress("UNREACHABLE_CODE")
class ChartActivity : MvpAppCompatActivity(), ChartView {

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
    private var repository = Repository()

    private var numDate = 0


    var clickOrNotSelectedDate = false
    val dateList = listOf("week", "month", "year")
    var numDateList = 0

    private val moxyPresenter by moxyPresenter { ChartPresenter(repository) }

    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        super.onCreate(savedInstanceState)
        binding = ChartActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val ownerName = intent.getSerializableExtra(KEY_NAME)
        val reposName = intent.getSerializableExtra(KEY_REPOS)
        val repository = Repository()
        val viewModelFactory = ChartViewFactory(repository)
        var dateSelected: Long = 0



        chartView = ViewModelProvider(this, viewModelFactory)[ChartViewModel::class.java]

        errorLog()
        selectRange()
        showGraph()

        barEntriesList = ArrayList()

        chartView.chartResponse.observe(this) { dateList ->

            dateResponseList = dateList

            barEntriesList = ArrayList()

            Log.d("RESPONSE_CHART_VIEW", "$dateResponseList")

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

    private fun selectRange() {
        binding.week.setOnClickListener {
            groupType = EnumRange.Companion.GroupType.WEEK
            selectedDate()
        }
        binding.month.setOnClickListener {
            groupType = EnumRange.Companion.GroupType.MONTH
            selectedDate()
        }
        binding.year.setOnClickListener {
            groupType = EnumRange.Companion.GroupType.YEAR
            selectedDate()
        }
    }

    private fun clickSelectRange(boolean: Boolean) {
        binding.week.isGone = boolean
        binding.month.isGone = boolean
        binding.year.isGone = boolean
        binding.backClick.isVisible = boolean
        binding.nextClick.isVisible = boolean
        binding.dateTv.isVisible = boolean
        binding.selectDateTv.isGone = boolean
        binding.showGraph.isVisible = boolean
    }

    private fun selectedDate() {
        clickSelectRange(true)
        binding.dateTv.text = moxyPresenter.clickBackOrNext(numDate, groupType)

        binding.nextClick.setOnClickListener {

            if (numDate == 0) {
                showError("Мы не можем смотреть в будущее :(")

            } else {
                numDate--
                val getDateNext = moxyPresenter.clickBackOrNext(numDate, groupType)
                binding.dateTv.text = getDateNext
            }
        }

        binding.backClick.setOnClickListener {
            numDate++
            val getDateBack = moxyPresenter.clickBackOrNext(numDate, groupType)
            binding.dateTv.text = getDateBack
        }
    }
    private fun showGraph() {
        binding.showGraph.setOnClickListener {
            val dateSelectedParse = DateConverter.convertTimestampToDate(binding.dateTv.text.toString())

        }
    }

    private fun barChartData() {
        val listBarChart = ArrayList<ArrayList<Int>>()
        val getGroupType = groupsType(groupType)
        val getRange = UniqueDate().getUniqueArrayList(getGroupType, numDate)

        val group = dateResponseList.groupBy {
            it.uniqueDate
        }

        for (i in 0 until getRange.size) {

            listBarChart.add(ArrayList())

            for (ind in 0 until group.keys.size) {

                if (getRange[i] == group.keys.elementAt(ind)) {
                    listBarChart[i].add(group.values.elementAt(ind).size)

                } else {
                    listBarChart[i].add(0)
                }

            }

            for (ind in 0 until listBarChart.size) {
                barEntriesList.add(BarEntry(ind.toFloat(),
                    group.values.elementAt(ind).size.toFloat()))
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
        dateSelected: Int,
    ) {
        return chartView.getReposStars(ownerName, reposName, groupType, dateSelected)
    }
//gulihua10010

    fun startSubActivity() {
        val chartIntent =
            SubscribersActivity.createSubscribeIntent(this@ChartActivity, dateResponseList)
        startActivity(chartIntent)
    }

    private fun errorLog() {
        chartView.error.observe(this) { error ->
            binding.error.text = error
            if (error != "") {
                binding.cardError.visibility = View.VISIBLE
            }
            binding.cardError.setOnClickListener {
                binding.cardError.visibility = View.GONE
            }
        }
        chartView.error.value = ""

    }

    override fun showError(message: String) {

    }

    override fun startSending(boolean: Boolean) {

    }

}