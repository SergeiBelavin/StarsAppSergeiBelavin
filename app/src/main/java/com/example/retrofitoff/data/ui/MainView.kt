package com.example.retrofitoff.data.ui

import com.arellomobile.mvp.MvpView
import com.example.retrofitoff.model.RepoUser

/**
* Если код работает, то: Created by Sergei Belavin on 18.12.2022.
 * Если нет, Created by Vasiliy Pupkov on 18.12.2022
*/
//@StateStrategyType(value = OneExecutionStateStrategy::class)
interface MainView: MvpView {

    fun showError(message: String)
    fun startSending()
    fun endSending()
}