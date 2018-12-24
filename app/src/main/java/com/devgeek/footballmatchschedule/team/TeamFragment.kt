package com.devgeek.footballmatchschedule.team


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout

import com.devgeek.footballmatchschedule.R
import com.devgeek.footballmatchschedule.api.ApiRepository
import com.devgeek.footballmatchschedule.team.detail.TeamDetailActivity
import com.devgeek.footballmatchschedule.invisible
import com.devgeek.footballmatchschedule.model.League
import com.devgeek.footballmatchschedule.model.Team
import com.devgeek.footballmatchschedule.presenter.SpinnerPresenter
import com.devgeek.footballmatchschedule.presenter.TeamPresenter
import com.devgeek.footballmatchschedule.view.SpinnerView
import com.devgeek.footballmatchschedule.view.TeamView
import com.devgeek.footballmatchschedule.visible
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_team.view.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.onRefresh

class TeamFragment : Fragment(), TeamView, SpinnerView {
    private var teams: MutableList<Team> = mutableListOf()
    private var league: ArrayList<League> = arrayListOf()
    private var leagueName: ArrayList<String> = arrayListOf()

    private lateinit var presenterTeam: TeamPresenter
    private lateinit var presenterSpinner: SpinnerPresenter
    private lateinit var adapter: TeamAdapter
    private lateinit var spinnerAdapter: ArrayAdapter<String>
    private lateinit var layoutView: View
    private lateinit var leagueTitle: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        layoutView = inflater.inflate(R.layout.fragment_team, container, false)

        val request = ApiRepository()
        val gson = Gson()

        presenterTeam = TeamPresenter(this, request, gson)
        presenterSpinner = SpinnerPresenter(this, request, gson)

        // Initialize spinner
        presenterSpinner.getAllLeague()

        spinnerAdapter = ArrayAdapter(layoutView.context, android.R.layout.simple_spinner_dropdown_item, leagueName)
        layoutView.spinner_team.adapter = spinnerAdapter

        layoutView.spinner_team.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                leagueTitle = layoutView.spinner_team.selectedItem.toString()

                presenterTeam.getTeamList(leagueTitle)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        adapter = TeamAdapter(teams) {
            startActivity<TeamDetailActivity>(
                    "team_id" to "${it.teamId}",
                    "team_name" to "${it.teamName}")
        }

        layoutView.rv_team_fragment.layoutManager = LinearLayoutManager(this.context, LinearLayout.VERTICAL, false)
        layoutView.rv_team_fragment.adapter = adapter

        adapter.notifyDataSetChanged()

        layoutView.swipe_refresh_team.onRefresh { presenterTeam.getTeamList(leagueTitle) }

        return layoutView
    }

    override fun showLoading() {
        layoutView.pb_team_fragment.visible()
    }

    override fun hideLoading() {
        layoutView.pb_team_fragment.invisible()
    }

    override fun showTeamList(data: List<Team>) {
        layoutView.swipe_refresh_team.isRefreshing = false
        teams.clear()
        teams.addAll(data)
        adapter.notifyDataSetChanged()
    }

    override fun showLeagueList(data: ArrayList<League>) {
        league.clear()
        league.addAll(data)

        leagueName.addAll(data.map { it.leagueName.toString() })
//        Log.d("league", league.toString())

        spinnerAdapter.notifyDataSetChanged()
    }

}
