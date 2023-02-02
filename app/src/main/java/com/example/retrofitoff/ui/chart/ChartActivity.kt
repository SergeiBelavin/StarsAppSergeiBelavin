package com.example.retrofitoff.ui.chart

import Repository
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.provider.Settings.Global.getString
import android.util.Half.toFloat
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.example.retrofitoff.R
import com.example.retrofitoff.data.entity.ChartListItem
import com.example.retrofitoff.model.StarGroup


import com.example.retrofitoff.databinding.ChartActivityBinding
import com.example.retrofitoff.ui.main.EnumRange
import com.example.retrofitoff.ui.sub.SubscribersActivity
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.omega_r.libs.omegatypes.Text
import kotlinx.coroutines.launch
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter
import java.util.*
import kotlin.collections.ArrayList

@Suppress("UNREACHABLE_CODE")
class ChartActivity : MvpAppCompatActivity(), ChartView {

    companion object {

        private val KEY_NAME = Text.from(R.string.intent_user_name).toString()
        private val KEY_REPOS = Text.from(R.string.intent_repo_name).toString()

        fun createIntent(context: Context, name: String, repo: String): Intent {
            return Intent(context, ChartActivity::class.java).putExtra(KEY_NAME, name)
                .putExtra(KEY_REPOS, repo)
        }

    }
    private val chartLog = Text.from(R.string.log_chart_activity)

    private lateinit var binding: ChartActivityBinding
    lateinit var barChart: BarChart
    lateinit var barData: BarData
    lateinit var barDataSet: BarDataSet
    lateinit var barEntriesList: ArrayList<BarEntry>
    private var groupType = EnumRange.Companion.GroupType.WEEK
    private var repository = Repository
    private var listStarRepo = emptyList<ChartListItem>()
    private val moxyPresenter by moxyPresenter { ChartPresenter(repository) }

    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        super.onCreate(savedInstanceState)

        binding = ChartActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        selectRange()
        barEntriesList = ArrayList()
        barEntriesList.add(BarEntry(1f, 1f))
        barEntriesList = ArrayList()
        barChartData()
        getChart()

        barChart.setOnClickListener {
            val avatarList = SubscribersActivity.createSubscribeIntent(this@ChartActivity, listStarRepo)
            startActivity(avatarList)
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
        binding.dateTv.text = moxyPresenter.clickBackOrNext(
            groupType,
            minusOrPlus = true,
            firstQuest = true
        )

        binding.nextClick.setOnClickListener {
            val getDateNext = moxyPresenter.clickBackOrNext(
                groupType,
                minusOrPlus = true,
                firstQuest = false
            )
            binding.dateTv.text = getDateNext
        }

        binding.backClick.setOnClickListener {
            val getDateBack = moxyPresenter.clickBackOrNext(
                groupType,
                minusOrPlus = false,
                firstQuest = false
            )
            binding.dateTv.text = getDateBack
        }
    }

    private fun barChartData() {
        barChart = binding.barChart
        barDataSet = BarDataSet(barEntriesList, R.string.number_of_stars.toString())
        barDataSet.valueTextColor = Color.BLACK
        barData = BarData(barDataSet)
        barChart.setFitBars(true)
        barChart.data = barData
        barChart.description.text = R.string.number_of_stars_by_date.toString()
        barDataSet.valueTextSize = 16f
        barChart.animateY(2000)
    }
//gulihua10010

    fun getChart() {
        binding.showGraph.setOnClickListener {

            moxyPresenter.dayForTheSchedule()

            val ownerName = intent.getSerializableExtra(KEY_NAME)
            val reposName = intent.getSerializableExtra(KEY_REPOS)

            lifecycleScope.launch {
                val chartDate = moxyPresenter.getReposStars(
                    ownerName.toString(),
                    reposName.toString(),
                    groupType,
                )
                listStarRepo = chartDate

                Log.d("${chartLog}_0", "rabotaet")
                Log.d("${chartLog}_1", "${chartDate}")
                if (chartDate.isEmpty()) {
                    Log.d("${chartLog}_2", "empty")
                } else {
                    Log.d("${chartLog}_3", "empty")
                }

                barEntriesList.clear()

                if (chartDate[0].starredAt.isNotEmpty()) {
                    Log.d("${chartLog}_0_EMPTY", "${chartDate[0].starredAt}")
                    for (i in 0 until chartDate[0].starredAt.size) {
                        barEntriesList.add(
                            BarEntry(
                                i.toFloat(),
                                chartDate[0].starredAt[i].toFloat()
                            )
                        )
                        Log.d("${chartLog}_BAR_ENTR", "$barEntriesList")
                    }
                } else {
                    Log.d("${chartLog}_CHART_EMPTY", "CHART_EMPTY")
                }
                barChartData()
            }

        }
    }

    override fun showError(message: Text) {
        binding.ErrorView.visibility = View.VISIBLE
        binding.error.text = message.toString()
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
