package com.devgeek.footballmatchschedule.view

import com.devgeek.footballmatchschedule.model.Match

interface MatchView {
    fun showLoading()
    fun hideLoading()
    fun showMatchList(data: List<Match>)
}