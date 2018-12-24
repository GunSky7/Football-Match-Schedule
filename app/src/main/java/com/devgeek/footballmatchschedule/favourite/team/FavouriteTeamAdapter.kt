package com.devgeek.footballmatchschedule.favourite.team

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.devgeek.footballmatchschedule.R
import com.devgeek.footballmatchschedule.database.FavouriteTeam
import com.devgeek.footballmatchschedule.model.Team
import com.devgeek.footballmatchschedule.team.TeamAdapter
import com.squareup.picasso.Picasso
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk25.coroutines.onClick

class FavouriteTeamAdapter(private val favouriteTeam: List<FavouriteTeam>, private val listener: (FavouriteTeam) -> Unit)
    : RecyclerView.Adapter<FavouriteTeamAdapter.FavouriteTeamViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteTeamViewHolder{
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.team_card, parent, false)

        return FavouriteTeamViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavouriteTeamViewHolder, position: Int) {
        holder.bindItem(favouriteTeam[position], listener)
    }

    override fun getItemCount(): Int = favouriteTeam.size

    class FavouriteTeamViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val imgvTeamBadge: ImageView = view.find(R.id.imgv_team_badge)
        private val tvTeamName: TextView = view.find(R.id.tv_team_name)

        fun bindItem(favouriteTeam: FavouriteTeam, listener: (FavouriteTeam) -> Unit) {
            Picasso.get().load(favouriteTeam.teamBadge).into(imgvTeamBadge)
            tvTeamName.text = favouriteTeam.teamName
            itemView.onClick { listener(favouriteTeam) }
        }
    }
}