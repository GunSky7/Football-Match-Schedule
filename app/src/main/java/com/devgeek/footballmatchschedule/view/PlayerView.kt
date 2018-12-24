package com.devgeek.footballmatchschedule.view

import com.devgeek.footballmatchschedule.model.Player

interface PlayerView {
    fun showLoading()
    fun hideLoading()
    fun showPlayerList(data: List<Player>)
}