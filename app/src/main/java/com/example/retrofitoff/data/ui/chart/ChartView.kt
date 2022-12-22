package com.example.retrofitoff.data.ui.chart

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

interface ChartView: MvpView {
    @AddToEndSingle
    fun showError(message: String)

    @AddToEndSingle
    fun startSending(boolean: Boolean)
}