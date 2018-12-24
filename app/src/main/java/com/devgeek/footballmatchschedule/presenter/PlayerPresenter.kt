package com.devgeek.footballmatchschedule.presenter

import com.devgeek.footballmatchschedule.CoroutineContextProvider
import com.devgeek.footballmatchschedule.api.ApiRepository
import com.devgeek.footballmatchschedule.api.TheSportDBApi
import com.devgeek.footballmatchschedule.apiresponse.LeagueResponse
import com.devgeek.footballmatchschedule.apiresponse.PlayerResponse
import com.devgeek.footballmatchschedule.apiresponse.TeamResponse
import com.devgeek.footballmatchschedule.view.PlayerView
import com.devgeek.footballmatchschedule.view.SpinnerView
import com.google.gson.Gson
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class PlayerPresenter(private val view: PlayerView, private val apiRepository: ApiRepository,
                       private val gson: Gson, private val context: CoroutineContextProvider = CoroutineContextProvider()) {
    fun getPlayerList(teamName: String?) {
        view.showLoading()
        doAsync {
            val data = gson.fromJson(apiRepository.doRequest(TheSportDBApi.getPlayers(teamName)),
                    PlayerResponse::class.java)

//            Log.d("data", data.toString())

            uiThread {
                view.showPlayerList(data.player)
                view.hideLoading()
            }
        }
    }

    fun getPlayerDetail(teamName: String?, playerName: String?) {
        view.showLoading()
        doAsync {
            val data = gson.fromJson(apiRepository.doRequest(TheSportDBApi.getPlayerDetail(teamName, playerName)),
                    PlayerResponse::class.java)

//            Log.d("data", data.toString())

            uiThread {
                view.showPlayerList(data.player)

                view.hideLoading()
            }
        }
    }
}