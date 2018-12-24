package com.devgeek.footballmatchschedule.favourite.match

import android.content.Intent
import android.provider.CalendarContract
import android.support.v4.content.ContextCompat
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import com.devgeek.footballmatchschedule.R
import com.devgeek.footballmatchschedule.database.FavouriteMatch
import com.devgeek.footballmatchschedule.invisible
import com.devgeek.footballmatchschedule.model.Match
import org.jetbrains.anko.*
import java.text.SimpleDateFormat
import java.util.*

class FavouriteMatchAdapter(private val favourite: List<FavouriteMatch>, private val listener: (FavouriteMatch) -> Unit)
    : RecyclerView.Adapter<FavouriteMatchAdapter.FavouriteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.match_card, parent, false)

        return FavouriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavouriteViewHolder, position: Int) {
        holder.bindItem(favourite[position], listener)
    }

    override fun getItemCount(): Int = favourite.size

    inner class FavouriteViewHolder(view: View) : RecyclerView.ViewHolder(view){

        private val tvMatchDate: TextView = view.find(R.id.tv_match_date)
        private val tvMatchTime: TextView = view.find(R.id.tv_match_time)
        private val tvHomeTeam: TextView = view.find(R.id.tv_home_team)
        private val tvHomeTeamScore: TextView = view.find(R.id.tv_home_team_score)
        private val tvAwayTeamScore: TextView = view.find(R.id.tv_away_team_score)
        private val tvAwayTeam: TextView = view.find(R.id.tv_away_team)
        private val cvMatchItems: CardView = view.find(R.id.cv_match_items)
        private val imgbtnCalendar: ImageButton = view.find(R.id.imgbtn_match_notification)

        fun bindItem(matchs: FavouriteMatch, listener: (FavouriteMatch) -> Unit) {
            tvHomeTeam.text = matchs.eventHomeTeam
            tvHomeTeamScore.text = matchs.eventHomeScore
            tvAwayTeam.text = matchs.eventAwayTeam
            tvAwayTeamScore.text = matchs.eventAwayScore

            imgbtnCalendar.invisible()

            // Change Match Date & Time to the desired format
            val date = matchs.eventDate.toString()
            val time = matchs.eventTime.toString()

            if (date.equals("null") && time.equals("null")) {
                tvMatchDate.text = ""
                tvMatchTime.text = ""
            } else {
                if (date.equals("null")) {
                    val dateTime = toGMTFormat("", time)
                    val formatterTime = SimpleDateFormat("HH:mm")

                    tvMatchDate.text = ""
                    tvMatchTime.text = formatterTime.format(dateTime)
                } else if (time.equals("null")) {
                    val dateTime = toGMTFormat(date, "")
                    val formatterDate = SimpleDateFormat("EEEE, dd MMM yyyy")

                    tvMatchDate.text = formatterDate.format(dateTime)
                    tvMatchTime.text = ""
                } else {
                    val dateTime = toGMTFormat(date, time)
                    val formatterDate = SimpleDateFormat("EEEE, dd MMM yyyy")
                    val formatterTime = SimpleDateFormat("HH:mm")
                    tvMatchDate.text = formatterDate.format(dateTime)
                    tvMatchTime.text = formatterTime.format(dateTime)
                }
            }

            cvMatchItems.setOnClickListener {
                listener(matchs)
            }
        }

        fun toGMTFormat(date: String, time: String): Date? {
            if (date.isEmpty()) {
                val formatter = SimpleDateFormat("HH:mm:ss")
                formatter.timeZone = TimeZone.getTimeZone("UTC")
                return formatter.parse(time)
            } else if (time.isEmpty()) {
                val formatter = SimpleDateFormat("dd/MM/yy")
                formatter.timeZone = TimeZone.getTimeZone("UTC")
                return formatter.parse(date)
            } else {
                val formatter = SimpleDateFormat("dd/MM/yy HH:mm:ss")
                formatter.timeZone = TimeZone.getTimeZone("UTC")
                val dateTime = "$date $time"
                return formatter.parse(dateTime)
            }

        }
    }

}

