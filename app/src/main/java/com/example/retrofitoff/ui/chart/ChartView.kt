package com.example.retrofitoff.ui.chart

import com.omega_r.libs.omegatypes.Text
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle


interface ChartView: MvpView {
    @AddToEndSingle
    fun showError(message: Text)

    @AddToEndSingle
    fun startSending(boolean: Boolean)
}