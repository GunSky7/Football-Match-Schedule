package com.devgeek.footballmatchschedule.presenter

import com.devgeek.footballmatchschedule.api.ApiRepository
import com.devgeek.footballmatchschedule.CoroutineContextProvider
import com.devgeek.footballmatchschedule.api.TheSportDBApi
import com.devgeek.footballmatchschedule.apiresponse.LeagueResponse
import com.devgeek.footballmatchschedule.view.SpinnerView
import com.google.gson.Gson
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class SpinnerPresenter(private val view: SpinnerView, private val apiRepository: ApiRepository,
                       private val gson: Gson, private val context: CoroutineContextProvider = CoroutineContextProvider()) {
    fun getAllLeague() {
        doAsync {
            val data = gson.fromJson(apiRepository.doRequest(TheSportDBApi.getAllLeagues()),
                    LeagueResponse::class.java)

//            Log.d("data", data.toString())

            uiThread {
                view.showLeagueList(data.countrys)
            }
        }
    }
}