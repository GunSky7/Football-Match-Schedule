package com.devgeek.footballmatchschedule.presenter

import com.devgeek.footballmatchschedule.api.ApiRepository
import com.devgeek.footballmatchschedule.CoroutineContextProvider
import com.devgeek.footballmatchschedule.api.TheSportDBApi
import com.devgeek.footballmatchschedule.apiresponse.MatchResponse
import com.devgeek.footballmatchschedule.apiresponse.SearchMatchResponse
import com.devgeek.footballmatchschedule.view.MatchView
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class MatchPresenter(private val view: MatchView, private val apiRepository: ApiRepository,
                     private val gson: Gson, private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    // Get list of 15 prev match from Api DB
    fun getPrevMatchList(id: String?) {
        view.showLoading()
        /*doAsync {
            val data = gson.fromJson(apiRepository
                    .doRequest(TheSportDBApi.getPrevMatch(id)),
                    MatchResponse::class.java
            )

            uiThread {
                view.hideLoading()
                view.showMatchList(data.events)
            }
        }*/

        async(context.main){
            val data = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.getPrevMatchs(id)),
                        MatchResponse::class.java
                )
            }

            view.showMatchList(data.await().events)
            view.hideLoading()
        }
    }

    // Get list of 15 next match from Api DB
    fun getNextMatchList(id: String?) {
        view.showLoading()
        /*doAsync {
            val data = gson.fromJson(apiRepository
                    .doRequest(TheSportDBApi.getNextMatch(id)),
                    MatchResponse::class.java
            )

            uiThread {
                view.hideLoading()
                view.showMatchList(data.events)
            }
        }*/

        async(context.main){
            val data = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.getNextMatchs(id)),
                        MatchResponse::class.java
                )
            }

            view.showMatchList(data.await().events)
            view.hideLoading()
        }
    }

    // Get match by name from Api DB
    fun getMatch(matchName: String?) {
        view.showLoading()

        async(context.main){
            val data = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.getMatchs(matchName)),
                        SearchMatchResponse::class.java
                )
            }

            view.showMatchList(data.await().event)
            view.hideLoading()
        }
    }
}