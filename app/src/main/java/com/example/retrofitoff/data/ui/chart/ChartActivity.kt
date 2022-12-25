package com.example.retrofitoff.data.ui.chart

import Repository
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
import androidx.lifecycle.lifecycleScope
import com.example.retrofitoff.data.repository.DateConverter
import com.example.retrofitoff.model.StarGroup


import com.example.retrofitoff.data.repository.UniqueDate
import com.example.retrofitoff.databinding.ChartActivityBinding
import com.example.retrofitoff.data.ui.main.EnumRange
import com.example.retrofitoff.data.ui.main.EnumRange.Companion.groupsType
import com.example.retrofitoff.data.ui.sub.SubscribersActivity
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import kotlinx.coroutines.launch
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
    lateinit var barChart: BarChart
    lateinit var barData: BarData
    lateinit var barDataSet: BarDataSet
    lateinit var barEntriesList: ArrayList<BarEntry>
    private var groupType = EnumRange.Companion.GroupType.WEEK
    private var dateResponseList = emptyList<StarGroup>()
    private var repository = Repository()
    private var numDate = 0
    private val moxyPresenter by moxyPresenter { ChartPresenter(repository) }

    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        super.onCreate(savedInstanceState)
        binding = ChartActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        selectRange()
        showGraph()

        barEntriesList = ArrayList()
        barEntriesList.add(BarEntry(1f, 1f))

            barEntriesList = ArrayList()

            Log.d("RESPONSE_CHART_VIEW", "$dateResponseList")

            barChartData()

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
        binding.dateTv.text = moxyPresenter.clickBackOrNext(numDate, groupType,
            minusOrPlus = true,
            firstQuest = true)

        binding.nextClick.setOnClickListener {
                val getDateNext = moxyPresenter.clickBackOrNext(numDate, groupType,
                    minusOrPlus = true,
                    firstQuest = false)
                binding.dateTv.text = getDateNext
        }

        binding.backClick.setOnClickListener {
            numDate++
            val getDateBack = moxyPresenter.clickBackOrNext(numDate, groupType,
                minusOrPlus = false,
                firstQuest = false)
            binding.dateTv.text = getDateBack
        }
    }

    private fun showGraph() {
        val ownerName = intent.getSerializableExtra(KEY_NAME)
        val reposName = intent.getSerializableExtra(KEY_REPOS)

        binding.showGraph.setOnClickListener {
            lifecycleScope.launch {
                moxyPresenter.getReposStars(ownerName.toString(),
                    reposName.toString(),
                    groupType,)
            }
        }
    }

    private fun barChartData() {
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
//gulihua10010

    fun startSubActivity() {
        val chartIntent =
            SubscribersActivity.createSubscribeIntent(this@ChartActivity, dateResponseList)
        startActivity(chartIntent)
    }

    override fun showError(message: String) {
        binding.ErrorView.visibility = View.VISIBLE
        binding.error.text = message
        binding.selectRangeDate.visibility = View.GONE
        binding.showGraph.visibility = View.GONE

        binding.okError.setOnClickListener {
            binding.ErrorView.visibility = View.INVISIBLE
            binding.selectRangeDate.visibility = View.VISIBLE
            binding.showGraph.visibility = View.VISIBLE
        }
    }

    override fun startSending(boolean: Boolean) {

    }

}
