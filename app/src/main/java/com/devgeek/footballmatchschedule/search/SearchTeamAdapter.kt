package com.devgeek.footballmatchschedule.search

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.devgeek.footballmatchschedule.R
import com.devgeek.footballmatchschedule.R.id.*
import com.devgeek.footballmatchschedule.invisible
import com.devgeek.footballmatchschedule.model.Match
import com.devgeek.footballmatchschedule.model.Team
import com.devgeek.footballmatchschedule.team.TeamAdapter
import com.squareup.picasso.Picasso
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk25.coroutines.onClick

class SearchTeamAdapter(private var teams: List<Team>, private val listener: (Team) -> Unit): RecyclerView.Adapter<SearchTeamAdapter.TeamViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.team_card, parent, false)

        return TeamViewHolder(view)
    }

    override fun getItemCount(): Int {
        return teams.size
    }

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        holder.bindItem(teams[position], listener)
    }

    class TeamViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val imgvTeamBadge: ImageView = view.find(R.id.imgv_team_badge)
        private val tvTeamName: TextView = view.find(R.id.tv_team_name)

        fun bindItem(teams: Team, listener: (Team) -> Unit) {
            Picasso.get().load(teams.teamBadge).into(imgvTeamBadge)
            tvTeamName.text = teams.teamName
            itemView.onClick { listener(teams) }
        }
    }
}
