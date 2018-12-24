package com.devgeek.footballmatchschedule.search

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import com.devgeek.footballmatchschedule.match.detail.MatchDetailActivity
import com.devgeek.footballmatchschedule.R
import com.devgeek.footballmatchschedule.R.id.add_to_favourite
import com.devgeek.footballmatchschedule.R.menu.search_menu
import com.devgeek.footballmatchschedule.api.ApiRepository
import com.devgeek.footballmatchschedule.team.detail.TeamDetailActivity
import com.devgeek.footballmatchschedule.invisible
import com.devgeek.footballmatchschedule.model.Match
import com.devgeek.footballmatchschedule.model.Team
import com.devgeek.footballmatchschedule.presenter.MatchPresenter
import com.devgeek.footballmatchschedule.presenter.TeamPresenter
import com.devgeek.footballmatchschedule.sharedpreferences.Preferences
import com.devgeek.footballmatchschedule.view.MatchView
import com.devgeek.footballmatchschedule.view.TeamView
import com.devgeek.footballmatchschedule.visible
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_search.*
import org.jetbrains.anko.startActivity

class SearchActivity : AppCompatActivity(), MatchView, TeamView {
    var TO_SEARCH_MATCHS = "matchs"

    private var matchs: MutableList<Match> = mutableListOf()
    private var teams: MutableList<Team> = mutableListOf()

    private var prefs: Preferences? = null

    private lateinit var toSearch: String
    private lateinit var presenterMatch: MatchPresenter
    private lateinit var presenterTeam: TeamPresenter
    private lateinit var adapterMatch: SearchMatchAdapter
    private lateinit var adapterTeam: SearchTeamAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        // Get from shared preference what we will search
        prefs = Preferences(this)
        toSearch = prefs!!.toSearch

        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val request = ApiRepository()
        val gson = Gson()

        if (toSearch.equals(TO_SEARCH_MATCHS)) {
            presenterMatch = MatchPresenter(this, request, gson)

            adapterMatch = SearchMatchAdapter(matchs) {
                startActivity<MatchDetailActivity>(
                    "event_id" to "${it.eventId}",
                    "event_name" to "${it.eventName}"
                )
            }

            rv_search.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
            rv_search.adapter = adapterMatch

            adapterMatch.notifyDataSetChanged()
        } else {
            presenterTeam = TeamPresenter(this, request, gson)

            adapterTeam = SearchTeamAdapter(teams) {
                startActivity<TeamDetailActivity>(
                    "team_id" to "${it.teamId}",
                    "team_name" to "${it.teamName}")
            }

            rv_search.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
            rv_search.adapter = adapterTeam

            adapterTeam.notifyDataSetChanged()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(search_menu, menu)

        val searchItem = menu!!.findItem(R.id.search)
        val searchView = searchItem.actionView as SearchView

        searchView.setIconified(false)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String?): Boolean {
                if (!newText.isNullOrEmpty()) {
                    if (toSearch.equals(TO_SEARCH_MATCHS)) {
                        presenterMatch.getMatch(newText)
                    } else {
                        presenterTeam.getTeam(newText)
                    }

                }

                return true
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun showLoading() {
        pb_search.visible()
    }

    override fun hideLoading() {
        pb_search.invisible()
    }

    override fun showMatchList(data: List<Match>) {
        matchs.clear()

        for (item in data) {
            if (item.eventSport.equals("Soccer")) {
                matchs.add(item)
            }
        }

        adapterMatch.notifyDataSetChanged()
    }

    override fun showTeamList(data: List<Team>) {
        teams.clear()

        for (item in data) {
            if (item.teamSport.equals("Soccer")) {
                teams.add(item)
            }
        }

        adapterTeam.notifyDataSetChanged()
    }
}
