package com.devgeek.footballmatchschedule.presenter

import com.devgeek.footballmatchschedule.api.ApiRepository
import com.devgeek.footballmatchschedule.TestContextProvider
import com.devgeek.footballmatchschedule.api.TheSportDBApi
import com.devgeek.footballmatchschedule.apiresponse.MatchResponse
import com.devgeek.footballmatchschedule.model.Match
import com.devgeek.footballmatchschedule.view.MatchView
import com.google.gson.Gson

import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class MatchPresenterTest {
    @Mock
    private
    lateinit var view: MatchView

    @Mock
    private
    lateinit var gson: Gson

    @Mock
    private
    lateinit var apiRepository: ApiRepository

    private lateinit var presenter: MatchPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = MatchPresenter(view, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun getPrevMatchList() {
        val match: MutableList<Match> = mutableListOf()
        val response = MatchResponse(match)

        Mockito.`when`(gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getPrevMatchs("4328")),
                MatchResponse::class.java
        )).thenReturn(response)

        presenter.getPrevMatchList("4328")

        verify(view).showLoading()
        Thread.sleep(2000)
        verify(view).showMatchList(match)
        verify(view).hideLoading()
    }

    @Test
    fun getNextMatchList() {
        val match: MutableList<Match> = mutableListOf()
        val response = MatchResponse(match)

        Mockito.`when`(gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getNextMatchs("4328")),
                MatchResponse::class.java
        )).thenReturn(response)

        presenter.getNextMatchList("4328")

        verify(view).showLoading()
        verify(view).showMatchList(match)
        verify(view).hideLoading()
    }
}