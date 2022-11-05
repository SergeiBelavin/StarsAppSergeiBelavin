package com.example.retrofitoff

import android.content.Context
import android.content.Intent

//data class IntentKey(val name: String, val repo: String, val context: Context) {
//    val intent = Intent(this, ChartRepoActivity::class.java)
//}

public class Intent(context: Context) {
   val i = Intent(context, ChartRepoActivity::class.java)

}