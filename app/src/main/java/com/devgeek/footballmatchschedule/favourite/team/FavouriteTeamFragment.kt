package com.devgeek.footballmatchschedule.favourite.team


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout

import com.devgeek.footballmatchschedule.R
import com.devgeek.footballmatchschedule.database.FavouriteMatch
import com.devgeek.footballmatchschedule.database.FavouriteTeam
import com.devgeek.footballmatchschedule.database.databaseMatch
import com.devgeek.footballmatchschedule.database.databaseTeam
import com.devgeek.footballmatchschedule.favourite.match.FavouriteMatchAdapter
import com.devgeek.footballmatchschedule.invisible
import com.devgeek.footballmatchschedule.match.detail.MatchDetailActivity
import com.devgeek.footballmatchschedule.team.detail.TeamDetailActivity
import com.devgeek.footballmatchschedule.visible
import kotlinx.android.synthetic.main.fragment_favourite_match.view.*
import kotlinx.android.synthetic.main.fragment_favourite_team.view.*
import org.jetbrains.anko.db.select
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.startActivity

class FavouriteTeamFragment : Fragment() {
    private var favourites: MutableList<FavouriteTeam> = mutableListOf()
    private lateinit var adapter: FavouriteTeamAdapter

    private lateinit var layoutView: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        layoutView = inflater.inflate(R.layout.fragment_favourite_team, container, false)

        adapter = FavouriteTeamAdapter(favourites) {
            startActivity<TeamDetailActivity>(
                    "team_id" to "${it.teamId}",
                    "team_name" to "${it.teamName}")
        }

        layoutView.pb_favourite_team_fragment.visible()

        showFavorite()

        layoutView.rv_favourite_team_fragment.layoutManager = LinearLayoutManager(this.context, LinearLayout.VERTICAL, false)
        layoutView.rv_favourite_team_fragment.adapter = adapter

        layoutView.swipe_refresh_favourite_team.onRefresh {
            favourites.clear()
            showFavorite()
        }

        return layoutView
    }

    private fun showFavorite(){
        context?.databaseTeam?.use {
            layoutView.swipe_refresh_favourite_team.isRefreshing = false
            val result = select(FavouriteTeam.TABLE_FAVOURITE_TEAM)
            val favourite = result.parseList(classParser<FavouriteTeam>())
            favourites.addAll(favourite)
            adapter.notifyDataSetChanged()
            layoutView.pb_favourite_team_fragment.invisible()
        }
    }
}
