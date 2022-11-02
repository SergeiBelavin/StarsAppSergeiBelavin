package com.example.retrofitoff

import android.R
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract.Data
import android.provider.ContactsContract.Intents
import android.util.Log
import androidx.annotation.RequiresApi

import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.toColorLong

import androidx.lifecycle.ViewModelProvider

import com.example.retrofitoff.databinding.ActivityChartRepoBinding
import com.example.retrofitoff.mode2.CharViewModel
import com.example.retrofitoff.mode2.StatiStarsUsers
import com.example.retrofitoff.mode2.StatiStarsUsersItem


import com.example.retrofitoff.repository.Repository
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONStringer
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList


class ChartRepoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChartRepoBinding
    private lateinit var viewModel2: CharViewModel
    private val JSONResponce = ArrayList<StatiStarsUsers>()

    lateinit var barChart: BarChart
    lateinit var barData: BarData
    lateinit var barDataSet: BarDataSet

    lateinit var mService: RetrofitInstance
    lateinit var dialog: AlertDialog

    lateinit var barEntriesList: ArrayList<BarEntry>

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChartRepoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val GET_KEY_NAME = "RepoName"
        val GET_KEY_REPOS = "UserName"

        val name = intent.getSerializableExtra(GET_KEY_NAME)
        val repos= intent.getSerializableExtra(GET_KEY_REPOS)
        binding.test.text = name.toString()

        val repository = Repository()
        val viewModelFactory = CharViewModelFactory(repository)

        viewModel2 = ViewModelProvider(this, viewModelFactory)[CharViewModel::class.java]

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        viewModel2.getPosStat(repos.toString(),name.toString())
        viewModel2.myResponse2.observe(this) { responce ->

            JSONResponce.add(responce.body()!!)
            val JSONRes = JSONResponce[0]

            Log.d("MyLog", "Log, ${JSONRes.size}")
            Log.d("MyLog", "Log, ${JSONRes[1]}")
//Добавить цикл
            val ObjStars = JSONRes[1].toString()
            val step1 = ObjStars.dropLast(11)
            val step2 = step1.drop(31)
            val step3StringForData = DateTimeFormatter.ofPattern("yyyy-dd-MM")
            val dDate: LocalDate = LocalDate.parse(step2, step3StringForData)
            val dDateMinus = dDate.minusDays(2)

            Log.d("MyLog1", "Log, $dDate")
            Log.d("MyLog1", "Log, $dDateMinus")

            //val calendar = Calendar.getInstance()
            //calendar.add()
        }

            barChart = binding.barChart

        getBarChartData()

        barDataSet = BarDataSet(barEntriesList, "Количество звезд по датам")
        barData = BarData(barDataSet)
        barChart.data = barData
        barDataSet.valueTextColor = Color.RED
        barDataSet.color = R.color.holo_purple
        barDataSet.valueTextSize = 18f
        barChart.description.isEnabled = false


        barChart.setOnClickListener{
            val i = Intent(this@ChartRepoActivity, MainActivity::class.java)
            startActivity(i)
        }

    }
    private fun settingGetBar() {

    }

    private fun getBarChartData() {
        barEntriesList = ArrayList()
        val stars1days = arrayListOf("2022-10-11",).size.toFloat()
        val stars2days = arrayListOf("2022-10-11", "2022-10-11",).size.toFloat()
        val stars3days = arrayListOf("2022-10-11", "2022-10-11","2022-10-11",).size.toFloat()
        val stars4days = arrayListOf("2022-10-11", "2022-10-11","2022-10-11","2022-10-11",).size.toFloat()
        val stars5days = arrayListOf("2022-10-11",).size.toFloat()
        val stars6days = arrayListOf("2022-10-11", "2022-10-11",).size.toFloat()
        val stars7days = arrayListOf("2022-10-11", "2022-10-11","2022-10-11",).size.toFloat()
        val stars8days = arrayListOf("2022-10-11", "2022-10-11","2022-10-11","2022-10-11",).size.toFloat()
        val stars9days = arrayListOf("2022-10-11", "2022-10-11","2022-10-11","2022-10-11","2022-10-11",).size.toFloat()
        val stars10days = arrayListOf("2022-10-11", "2022-10-11","2022-10-11","2022-10-11","2022-10-11","2022-10-11",).size.toFloat()
        val stars11days = arrayListOf("2022-10-11", "2022-10-11","2022-10-11","2022-10-11","2022-10-11","2022-10-11","2022-10-11").size.toFloat()
        val stars12days = arrayListOf("2022-10-11", "2022-10-11","2022-10-11","2022-10-11","2022-10-11","2022-10-11",).size.toFloat()
        val stars13days = arrayListOf("2022-10-11", "2022-10-11","2022-10-11","2022-10-11","2022-10-11",).size.toFloat()
        val stars14days = arrayListOf("2022-10-11", "2022-10-11",).size.toFloat()

        barEntriesList.add(BarEntry(1f, stars1days))
        barEntriesList.add(BarEntry(2f, stars2days))
        barEntriesList.add(BarEntry(3f, stars3days))
        barEntriesList.add(BarEntry(4f, stars4days))
        barEntriesList.add(BarEntry(5f, stars5days))
        barEntriesList.add(BarEntry(6f, stars6days))
        barEntriesList.add(BarEntry(7f,stars7days))
        barEntriesList.add(BarEntry(8f,stars8days))
        barEntriesList.add(BarEntry(9f,stars9days))
        barEntriesList.add(BarEntry(10f,stars10days))
        barEntriesList.add(BarEntry(11f,stars11days))
        barEntriesList.add(BarEntry(12f,stars12days))
        barEntriesList.add(BarEntry(13f,stars13days))
        barEntriesList.add(BarEntry(14f,stars14days))

    }

}