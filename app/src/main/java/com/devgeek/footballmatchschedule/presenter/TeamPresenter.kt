package com.devgeek.footballmatchschedule.presenter

import com.devgeek.footballmatchschedule.CoroutineContextProvider
import com.devgeek.footballmatchschedule.api.ApiRepository
import com.devgeek.footballmatchschedule.api.TheSportDBApi
import com.devgeek.footballmatchschedule.apiresponse.SearchMatchResponse
import com.devgeek.footballmatchschedule.apiresponse.TeamResponse
import com.devgeek.footballmatchschedule.model.Team
import com.devgeek.footballmatchschedule.view.TeamView
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

data class TeamPresenter(private val view: TeamView, private val apiRepository: ApiRepository,
                         private val gson: Gson, private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getTeamList(league: String?) {
        view.showLoading()

        async(context.main){
            val data = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.getAllTeams(league)),
                        TeamResponse::class.java
                )
            }
            view.showTeamList(data.await().teams)
            view.hideLoading()
        }
    }

    // Get team by name from Api DB
    fun getTeam(teamName: String?) {
        view.showLoading()

        async(context.main){
            val data = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.getTeams(teamName)),
                        TeamResponse::class.java
                )
            }

            view.showTeamList(data.await().teams)
            view.hideLoading()
        }
    }

    // Get team detail by name from Api DB
    fun getTeamDetail(teamId: String?) {
        view.showLoading()

        async(context.main){
            val data = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.getTeamDetail(teamId)),
                        TeamResponse::class.java
                )
            }

            view.showTeamList(data.await().teams)
            view.hideLoading()
        }
    }
}