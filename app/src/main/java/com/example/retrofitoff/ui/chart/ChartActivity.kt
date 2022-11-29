package com.example.retrofitoff.ui.chart

import android.R
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Toast
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
import com.github.mikephil.charting.utils.ColorTemplate
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
    private var groupType = Repository.GroupType.FOURTEEN_DAYS

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChartRepoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repository = Repository()
        val viewModelFactory = ChartViewFactory(repository)

        chartView = ViewModelProvider(this, viewModelFactory)[ChartViewModel::class.java]

        val ownerName = intent.getSerializableExtra(KEY_NAME)
        val reposName = intent.getSerializableExtra(KEY_REPOS)
        dateResponseList = ArrayList()
        barEntriesList = ArrayList()
        val reposStar = chartView.getReposStars(ownerName.toString(),reposName.toString(),groupType)

        binding.fourteenDaysLong.setOnClickListener {
            groupType = Repository.GroupType.FOURTEEN_DAYS
            newResponse()
            reposStar
        }
        binding.thirtyDaysLong.setOnClickListener{
            groupType = Repository.GroupType.THIRTY_DAYS
            newResponse()
            reposStar
        }
        binding.sixtyDaysLong.setOnClickListener {
            groupType = Repository.GroupType.SIXTY_DAYS
            newResponse()
            reposStar
        }
            chartView.chartResponse.observe(this) { dateList ->
                dateResponseList.add(dateList)
                Log.d("DATE_LIST_SIZE", "${dateList.size}")

                if(dateList.size == groupsType(groupType)) {
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

                    Log.d("MyLogEn", "$barEntriesList")
                    barChart.animateY(2000)

                    Log.d("MyLogEn", "$barEntriesList")
                    barChart.animateY(2000)
                } else Toast.makeText(this, "Репозиторий создан менее ${groupsType(groupType)} дней назад",
                Toast.LENGTH_LONG).show()
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
        val chartList = ArrayList<Float>()
        for (i in 0 until getGroupType) {
            val sizeResponse = dateResponseList[0][i].size.toFloat()
            chartList.add(sizeResponse)

            Log.d("DATE_RESP_LIST_TEST", "${chartList}")
            Log.d("DATE_RESP_FLOAT", "${i.toFloat()}, $sizeResponse")
            barEntriesList.add(BarEntry(i.toFloat(), 1f))
        }
        for (i in 0 until getGroupType) {
            barEntriesList[i] = BarEntry(i.toFloat()+1f, chartList[i])
        }
        // gulihua10010
    }

    fun newResponse() {
        dateResponseList = ArrayList()
        barEntriesList = ArrayList()
    }

}
