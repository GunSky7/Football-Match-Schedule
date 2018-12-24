package com.devgeek.footballmatchschedule.view

import com.devgeek.footballmatchschedule.model.League

interface SpinnerView {
    fun showLeagueList(data: ArrayList<League>)
}