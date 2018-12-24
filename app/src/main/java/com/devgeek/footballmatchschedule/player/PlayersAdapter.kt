package com.devgeek.footballmatchschedule.player

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.devgeek.footballmatchschedule.R
import com.devgeek.footballmatchschedule.model.Player
import com.squareup.picasso.Picasso
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk25.coroutines.onClick

class PlayersAdapter(private val players: List<Player>, private val listener: (Player) -> Unit)
    : RecyclerView.Adapter<PlayersAdapter.PlayerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.player_card, parent, false)

        return PlayerViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        holder.bindItem(players[position], listener)
    }

    override fun getItemCount(): Int = players.size

    class PlayerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val imgvPlayerImage: ImageView = view.find(R.id.imgv_player_image)
        private val tvPlayerName: TextView = view.find(R.id.tv_player_name)
        private val tvPlayerPosition: TextView = view.find(R.id.tv_player_position)

        fun bindItem(players: Player, listener: (Player) -> Unit) {
            Picasso.get().load(players.playerImage).into(imgvPlayerImage)
            tvPlayerName.text = players.playerName
            tvPlayerPosition.text = players.playerPosition
            itemView.onClick { listener(players) }
        }
    }
}