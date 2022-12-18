package com.example.retrofitoff.mvp.views

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.example.retrofitoff.model.RepoUser

/**
* Если код работает, то: Created by Sergei Belavin on 18.12.2022.
 * Если нет, Created by Vasiliy Pupkov on 18.12.2022
*/
@StateStrategyType(value = OneExecutionStateStrategy::class)
interface MainView: MvpView {
    fun onClickAdapter(list: RepoUser)
    fun showError(message: String)
}