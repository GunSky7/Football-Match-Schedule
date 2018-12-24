package com.devgeek.footballmatchschedule.player


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout

import com.devgeek.footballmatchschedule.R
import com.devgeek.footballmatchschedule.api.ApiRepository
import com.devgeek.footballmatchschedule.invisible
import com.devgeek.footballmatchschedule.model.Player
import com.devgeek.footballmatchschedule.player.detail.PlayerDetailActivity
import com.devgeek.footballmatchschedule.presenter.PlayerPresenter
import com.devgeek.footballmatchschedule.view.PlayerView
import com.devgeek.footballmatchschedule.visible
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_player.view.*
import org.jetbrains.anko.support.v4.startActivity

class PlayersFragment : Fragment(), PlayerView {
    private var players: MutableList<Player> = mutableListOf()
    private lateinit var presenter: PlayerPresenter
    private lateinit var adapter: PlayersAdapter

    private lateinit var layoutView: View
    private lateinit var teamName: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        layoutView = inflater.inflate(R.layout.fragment_player, container, false)

        val intent = activity!!.intent
        teamName = intent!!.getStringExtra("team_name")

        val request = ApiRepository()
        val gson = Gson()

        presenter = PlayerPresenter(this, request, gson)
        presenter.getPlayerList(teamName)

        adapter = PlayersAdapter(players) {
            startActivity<PlayerDetailActivity>(
                    "player_name" to "${it.playerName}",
                    "team_name" to teamName)
        }

        layoutView.rv_player_fragment.layoutManager = LinearLayoutManager(this.context, LinearLayout.VERTICAL, false)
        layoutView.rv_player_fragment.adapter = adapter

        return layoutView
    }

    override fun showLoading() {
        layoutView.pb_player.visible()
    }

    override fun hideLoading() {
        layoutView.pb_player.invisible()
    }

    override fun showPlayerList(data: List<Player>) {
        players.clear()
        players.addAll(data)
        adapter.notifyDataSetChanged()
    }
}
