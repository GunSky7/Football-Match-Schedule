package com.devgeek.footballmatchschedule.team.detail

import android.database.sqlite.SQLiteConstraintException
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v4.content.ContextCompat

import android.view.Menu
import android.view.MenuItem
import com.devgeek.footballmatchschedule.R
import com.devgeek.footballmatchschedule.api.ApiRepository
import com.devgeek.footballmatchschedule.database.FavouriteMatch
import com.devgeek.footballmatchschedule.database.FavouriteTeam
import com.devgeek.footballmatchschedule.database.databaseMatch
import com.devgeek.footballmatchschedule.database.databaseTeam
import com.devgeek.footballmatchschedule.invisible
import com.devgeek.footballmatchschedule.model.Team
import com.devgeek.footballmatchschedule.presenter.TeamPresenter
import com.devgeek.footballmatchschedule.view.TeamView
import com.devgeek.footballmatchschedule.visible
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_team_detail.*
import kotlinx.coroutines.experimental.selects.select
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.snackbar

class TeamDetailActivity : AppCompatActivity(), TeamView {
    private lateinit var presenter: TeamPresenter
    private lateinit var team: Team

    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false
    private lateinit var id: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_detail)

        val intent = intent
        id = intent.getStringExtra("team_id")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val request = ApiRepository()
        val gson = Gson()
        presenter = TeamPresenter(this, request, gson)
        presenter.getTeamDetail(id)

        val fragmentAdapter = TeamDetailPagerAdapter(supportFragmentManager)

        viewpager_team_detail.adapter = fragmentAdapter
        tabs_team_detail.setupWithViewPager(viewpager_team_detail)

        favouriteState()
    }

    override fun showLoading() {
        pb_team_detail.visible()
    }

    override fun hideLoading() {
        pb_team_detail.invisible()
    }

    override fun showTeamList(data: List<Team>) {
        team = Team(data[0].teamId, data[0].teamName,
                data[0].teamBadge, data[0].teamFormedYear,
                data[0].teamStadium, data[0].teamDescription)

        Picasso.get().load(team.teamBadge).into(imgv_team_detail_badge)
        tv_team_detail_name.text = team.teamName
        tv_team_detail_year.text = team.teamFormedYear
        tv_team_detail_stadium.text = team.teamStadium
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
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
            R.id.add_to_favourite -> {
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
            databaseTeam.use {
                insert(FavouriteTeam.TABLE_FAVOURITE_TEAM,
                        FavouriteTeam.TEAM_ID to team.teamId,
                        FavouriteTeam.TEAM_NAME to team.teamName,
                        FavouriteTeam.TEAM_BADGE to team.teamBadge)
            }
            snackbar(activity_team_detail, "Added to favourite").show()
        } catch (e: SQLiteConstraintException){
            snackbar(activity_team_detail, e.localizedMessage).show()
        }
    }

    private fun setFavourite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_added_to_favourites)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_add_to_favourites)
    }

    private fun favouriteState(){
        databaseTeam.use {
            val result = select(FavouriteTeam.TABLE_FAVOURITE_TEAM)
                    .whereArgs("(TEAM_ID = {id})",
                            "id" to id)
            val favorite = result.parseList(classParser<FavouriteTeam>())
            if (!favorite.isEmpty()) isFavorite = true
        }
    }

    private fun removeFromFavourite(){
        try {
            databaseTeam.use {
                delete(FavouriteTeam.TABLE_FAVOURITE_TEAM, "(TEAM_ID = {id})",
                        "id" to id)
            }
            snackbar(activity_team_detail, "Removed from favourite").show()
        } catch (e: SQLiteConstraintException){
            snackbar(activity_team_detail, e.localizedMessage).show()
        }
    }
}
