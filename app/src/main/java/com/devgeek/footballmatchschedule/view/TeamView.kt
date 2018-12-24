package com.devgeek.footballmatchschedule.view

import com.devgeek.footballmatchschedule.model.Team

interface TeamView {
    fun showLoading()
    fun hideLoading()
    fun showTeamList(data: List<Team>)
}