package com.example.retrofitoff.data.ui.main

import com.example.retrofitoff.model.RepoUser
import moxy.MvpView
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType
import moxy.viewstate.strategy.alias.AddToEndSingle

/**
* Если код работает, то: Created by Sergei Belavin on 18.12.2022.
 * Если нет, Created by Vasiliy Pupkov on 18.12.2022
*/

interface MainView: MvpView {

    @AddToEndSingle
    fun showError(message: String)

    @AddToEndSingle
    fun startSending(boolean: Boolean)

    @AddToEndSingle
    fun endSending()
}