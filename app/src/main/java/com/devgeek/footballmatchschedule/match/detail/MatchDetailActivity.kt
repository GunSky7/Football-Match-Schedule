package com.devgeek.footballmatchschedule.match.detail

import android.database.sqlite.SQLiteConstraintException
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.Menu
import android.view.MenuItem
import com.devgeek.footballmatchschedule.R
import com.devgeek.footballmatchschedule.R.drawable.ic_add_to_favourites
import com.devgeek.footballmatchschedule.R.drawable.ic_added_to_favourites
import com.devgeek.footballmatchschedule.R.id.add_to_favourite
import com.devgeek.footballmatchschedule.R.menu.detail_menu
import com.devgeek.footballmatchschedule.apiresponse.TeamResponse
import com.devgeek.footballmatchschedule.api.ApiRepository
import com.devgeek.footballmatchschedule.api.TheSportDBApi
import com.devgeek.footballmatchschedule.database.databaseMatch
import com.devgeek.footballmatchschedule.database.FavouriteMatch
import com.devgeek.footballmatchschedule.model.Match
import com.devgeek.footballmatchschedule.presenter.MatchPresenter
import com.devgeek.footballmatchschedule.view.MatchView
import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_team_detail.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.text.SimpleDateFormat
import java.util.*

class MatchDetailActivity : AppCompatActivity(), MatchView {
    private lateinit var detailMatch: Match

    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false

    private var homeTeamBadge: String? = null
    private var awayTeamBadge: String? = null

    private lateinit var presenter: MatchPresenter
    private lateinit var eventId: String
    private lateinit var eventName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // Get Intent
        val intent = intent
        eventId = intent.getStringExtra("event_id")
        eventName = intent.getStringExtra("event_name")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val request = ApiRepository()
        val gson = Gson()

        presenter = MatchPresenter(this, request, gson)
        presenter.getMatch(eventName)

        favouriteState()
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

    fun getBadgeTeam(teamName: String, team: String) {
        val apiRepository = ApiRepository()
        val gson = Gson()

        doAsync {
            val data = gson.fromJson(apiRepository
                    .doRequest(TheSportDBApi.getTeams(teamName)),
                    TeamResponse::class.java
            )

            uiThread {
                if (team.equals("home")) {
                    homeTeamBadge = data.teams[0].teamBadge
                    Picasso.get().load(homeTeamBadge).into(imgv_detail_home_badge)
                } else {
                    awayTeamBadge = data.teams[0].teamBadge
                    Picasso.get().load(awayTeamBadge).into(imgv_detail_away_badge)
                }
            }
        }
    }

    fun stringSplitter(stringToSplit: String): String {
        if (stringToSplit.equals("null")) return ""

        val str = stringToSplit.split(";")
        var result = str[0]

        for (indx in str.indices) {
            if (indx > 0) {
                result = result + "\n" + str[indx]
            }
        }

        return result
    }

    override fun showLoading() {
        //
    }

    override fun hideLoading() {
        //
    }

    override fun showMatchList(data: List<Match>) {
        detailMatch = Match(data[0].eventId, data[0].eventName, data[0].eventHomeTeam, data[0].eventHomeScore,
                data[0].eventHomeShots, data[0].eventHomeGoalDetails, data[0].eventHomeLineupGoalkeeper,
                data[0].eventHomeLineupDefence, data[0].eventHomeLineupMidfield, data[0].eventHomeLineupForward,
                data[0].eventHomeLineupSubtitutes, data[0].eventAwayTeam, data[0].eventAwayScore,
                data[0].eventAwayShots, data[0].eventAwayGoalDetails, data[0].eventAwayLineupGoalkeeper,
                data[0].eventAwayLineupDefence, data[0].eventAwayLineupMidfield, data[0].eventAwayLineupForward,
                data[0].eventAwayLineupSubtitutes, data[0].eventDate, data[0].eventTime)

        // Home Team Detail
        tv_detail_home_team.text = detailMatch.eventHomeTeam
        tv_detail_home_score.text = detailMatch.eventHomeScore
        tv_detail_home_goals.text = stringSplitter(detailMatch.eventHomeGoalDetails.toString())
        tv_detail_home_shots.text = stringSplitter(detailMatch.eventHomeShots.toString())
        tv_detail_home_goalkeeper.text = stringSplitter(detailMatch.eventHomeLineupGoalkeeper.toString())
        tv_detail_home_defense.text = stringSplitter(detailMatch.eventHomeLineupDefence.toString())
        tv_detail_home_midfield.text = stringSplitter(detailMatch.eventHomeLineupMidfield.toString())
        tv_detail_home_forward.text = stringSplitter(detailMatch.eventHomeLineupForward.toString())
        tv_detail_home_subtitutes.text = stringSplitter(detailMatch.eventHomeLineupSubtitutes.toString())

        // Get Home Team Badge
        getBadgeTeam(detailMatch.eventHomeTeam.toString(), "home")

        // Away Team Detail
        tv_detail_away_team.text = detailMatch.eventAwayTeam
        tv_detail_away_score.text = detailMatch.eventAwayScore
        tv_detail_away_goals.text = stringSplitter(detailMatch.eventAwayGoalDetails.toString())
        tv_detail_away_shots.text = stringSplitter(detailMatch.eventAwayShots.toString())
        tv_detail_away_goalkeeper.text = stringSplitter(detailMatch.eventAwayLineupGoalkeeper.toString())
        tv_detail_away_defense.text = stringSplitter(detailMatch.eventAwayLineupDefence.toString())
        tv_detail_away_midfield.text = stringSplitter(detailMatch.eventAwayLineupMidfield.toString())
        tv_detail_away_forward.text = stringSplitter(detailMatch.eventAwayLineupForward.toString())
        tv_detail_away_subtitutes.text = stringSplitter(detailMatch.eventAwayLineupSubtitutes.toString())

        // Get Away Team Badge
        getBadgeTeam(detailMatch.eventAwayTeam.toString(), "away")

        // Change Match Date & Time to the desired format
        val date = detailMatch.eventDate.toString()
        val time = detailMatch.eventTime.toString()

        if (date.equals("null") && time.equals("null")) {
            tv_detail_match_date.text = ""
            tv_detail_match_time.text = ""
        } else {
            if (date.equals("null")) {
                val dateTime = toGMTFormat("", time)
                val formatterTime = SimpleDateFormat("HH:mm")

                tv_detail_match_date.text = ""
                tv_detail_match_time.text = formatterTime.format(dateTime)
            } else if (time.equals("null")) {
                val dateTime = toGMTFormat(date, "")
                val formatterDate = SimpleDateFormat("EEEE, dd MMM yyyy")

                tv_detail_match_date.text = formatterDate.format(dateTime)
                tv_detail_match_time.text = ""
            } else {
                val dateTime = toGMTFormat(date, time)
                val formatterDate = SimpleDateFormat("EEEE, dd MMM yyyy")
                val formatterTime = SimpleDateFormat("HH:mm")
                tv_detail_match_date.text = formatterDate.format(dateTime)
                tv_detail_match_time.text = formatterTime.format(dateTime)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(detail_menu, menu)
        menuItem = menu
        setFavourite()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            add_to_favourite -> {
                if (isFavorite) removeFromFavourite() else addToFavourite()

                isFavorite = !isFavorite
                setFavourite()

                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun addToFavourite(){
        try {
            databaseMatch.use {
                insert(FavouriteMatch.TABLE_FAVOURITE_MATCH,
                        FavouriteMatch.EVENT_ID to detailMatch.eventId,
                        FavouriteMatch.EVENT_NAME to detailMatch.eventName,
                        FavouriteMatch.EVENT_HOME_TEAM to detailMatch.eventHomeTeam,
                        FavouriteMatch.EVENT_HOME_SCORE to detailMatch.eventHomeScore,
                        FavouriteMatch.EVENT_AWAY_TEAM to detailMatch.eventAwayTeam,
                        FavouriteMatch.EVENT_AWAY_SCORE to detailMatch.eventAwayScore,
                        FavouriteMatch.EVENT_DATE to detailMatch.eventDate,
                        FavouriteMatch.EVENT_TIME to detailMatch.eventTime)
            }
            snackbar(ll_detail,"Added to favourite").show()
        } catch (e: SQLiteConstraintException){
            snackbar(ll_detail, e.localizedMessage).show()
        }
    }

    private fun setFavourite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_added_to_favourites)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_add_to_favourites)
    }

    private fun favouriteState(){
        databaseMatch.use {
            val result = select(FavouriteMatch.TABLE_FAVOURITE_MATCH)
                    .whereArgs("(EVENT_ID = {id})",
                            "id" to eventId)
            val favourite = result.parseList(classParser<FavouriteMatch>())
            if (!favourite.isEmpty()) isFavorite = true
        }
    }

    private fun removeFromFavourite(){
        try {
            databaseMatch.use {
                delete(FavouriteMatch.TABLE_FAVOURITE_MATCH, "(EVENT_ID = {id})",
                        "id" to eventId)
            }
            snackbar(ll_detail, "Removed to favourite").show()
        } catch (e: SQLiteConstraintException){
            snackbar(ll_detail, e.localizedMessage).show()
        }
    }
}
