package com.devgeek.footballmatchschedule.player.detail

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.devgeek.footballmatchschedule.R
import com.devgeek.footballmatchschedule.R.id.add_to_favourite
import com.devgeek.footballmatchschedule.api.ApiRepository
import com.devgeek.footballmatchschedule.invisible
import com.devgeek.footballmatchschedule.model.Player
import com.devgeek.footballmatchschedule.player.PlayersAdapter
import com.devgeek.footballmatchschedule.presenter.PlayerPresenter
import com.devgeek.footballmatchschedule.view.PlayerView
import com.devgeek.footballmatchschedule.visible
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_player_detail.*

class PlayerDetailActivity : AppCompatActivity(), PlayerView {
    private lateinit var presenter: PlayerPresenter

    private lateinit var teamName: String
    private lateinit var playerName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_detail)

        val intent = intent
        teamName = intent.getStringExtra("team_name")
        playerName = intent.getStringExtra("player_name")

        supportActionBar?.title = playerName
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val request = ApiRepository()
        val gson = Gson()

        presenter = PlayerPresenter(this, request, gson)
        presenter.getPlayerDetail(teamName, playerName)
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
        pb_player_detail.visible()
    }

    override fun hideLoading() {
        pb_player_detail.invisible()
    }

    override fun showPlayerList(data: List<Player>) {
        Picasso.get().load(data[0].playerFanArt).into(imgv_player_detail_fanart)

        tv_player_detail_weight.text = data[0].playerWeight
        tv_player_detail_height.text = data[0].playerHeight
        tv_player_detail_position.text = data[0].playerPosition
        tv_player_detail_description.text = data[0].playerDescription
    }
}
