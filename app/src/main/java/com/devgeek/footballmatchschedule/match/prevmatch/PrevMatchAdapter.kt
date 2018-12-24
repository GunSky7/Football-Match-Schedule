package com.devgeek.footballmatchschedule.match.prevmatch

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import com.devgeek.footballmatchschedule.R
import com.devgeek.footballmatchschedule.R.id.*
import com.devgeek.footballmatchschedule.invisible
import com.devgeek.footballmatchschedule.model.Match
import org.jetbrains.anko.find
import java.text.SimpleDateFormat
import java.util.*

class PrevMatchAdapter(private var matchs: List<Match>, private val listener: (Match) -> Unit): RecyclerView.Adapter<PrevMatchAdapter.MatchViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.match_card, parent, false)

        return MatchViewHolder(view)
    }

    override fun getItemCount(): Int {
        return matchs.size
    }

    override fun onBindViewHolder(holder: MatchViewHolder, position: Int) {
        holder.bindItem(matchs[position], listener)
    }

    class MatchViewHolder(view: View) : RecyclerView.ViewHolder(view){

        private val tvMatchDate: TextView = view.find(tv_match_date)
        private val tvMatchTime: TextView = view.find(tv_match_time)
        private val tvHomeTeam: TextView = view.find(tv_home_team)
        private val tvHomeTeamScore: TextView = view.find(tv_home_team_score)
        private val tvAwayTeamScore: TextView = view.find(tv_away_team_score)
        private val tvAwayTeam: TextView = view.find(tv_away_team)
        private val cvMatchItems: CardView = view.find(cv_match_items)
        private val imgbtnCalendar: ImageButton = view.find(imgbtn_match_notification)

        fun bindItem(matchs: Match, listener: (Match) -> Unit) {
            tvHomeTeam.text = matchs.eventHomeTeam
            tvHomeTeamScore.text = matchs.eventHomeScore
            tvAwayTeam.text = matchs.eventAwayTeam
            tvAwayTeamScore.text = matchs.eventAwayScore

            imgbtnCalendar.invisible()

            // Change Match Date & Time to the desired format
            val dateTime = toGMTFormat(matchs.eventDate.toString(), matchs.eventTime.toString())
            val formatterDate = SimpleDateFormat("EEEE, dd MMM yyyy")
            val formatterTime = SimpleDateFormat("HH:mm")
            tvMatchDate.text = formatterDate.format(dateTime)
            tvMatchTime.text = formatterTime.format(dateTime)

            cvMatchItems.setOnClickListener {
                listener(matchs)
            }
        }

        fun toGMTFormat(date: String, time: String): Date? {
            val formatter = SimpleDateFormat("dd/MM/yy HH:mm:ss")
            formatter.timeZone = TimeZone.getTimeZone("UTC")
            val dateTime = "$date $time"
            return formatter.parse(dateTime)
        }
    }
}
