package com.devgeek.footballmatchschedule

import android.content.SharedPreferences
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.view.Menu
import android.view.MenuItem
import com.devgeek.footballmatchschedule.R.id.*
import com.devgeek.footballmatchschedule.R.menu.home_menu
import com.devgeek.footballmatchschedule.R.menu.search_menu
import com.devgeek.footballmatchschedule.favourite.FavouriteFragment
import com.devgeek.footballmatchschedule.match.MatchFragment
import com.devgeek.footballmatchschedule.match.nextmatch.NextFragment
import com.devgeek.footballmatchschedule.search.SearchActivity
import com.devgeek.footballmatchschedule.sharedpreferences.Preferences
import com.devgeek.footballmatchschedule.team.TeamFragment
import kotlinx.android.synthetic.main.activity_home.*
import org.jetbrains.anko.startActivity

class HomeActivity : AppCompatActivity() {
    var prefs: Preferences? = null
    var TO_SEARCH_MATCHS = "matchs"
    var TO_SEARCH_TEAMS = "teams"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        prefs = Preferences(this)

        // Setting the Bottom Navigation
        bottom_navigation.setOnNavigationItemSelectedListener {item ->
            when (item.itemId) {
                navigation_match -> {
                    val fragment = MatchFragment()

                    prefs!!.toSearch = TO_SEARCH_MATCHS

                    // change the main fragment to PrevFragment
                    fragmentController(savedInstanceState, fragment)
                }

                navigation_team -> {
                    val fragment = TeamFragment()

                    prefs!!.toSearch = TO_SEARCH_TEAMS

                    // change the main fragment to PrevFragment
                    fragmentController(savedInstanceState, fragment)
                }

                navigation_favourites -> {
                    val fragment = FavouriteFragment()

                    // change the main fragment to FavouriteFragment
                    fragmentController(savedInstanceState, fragment)
                }
            }

            true
        }

        bottom_navigation.selectedItemId = navigation_match
    }
/*
    // Navigation Bottom chooser
    private val onNavigationItemSelectedListener = object : BottomNavigationView.OnNavigationItemSelectedListener {
        override fun onNavigationItemSelected(item: MenuItem): Boolean {
            when (item.itemId) {
                navigation_match -> {
                    val fragment = MatchFragment()

                    prefs!!.toSearch = TO_SEARCH_MATCHS

                    // change the main fragment to PrevFragment
                    fragmentController(fragment)

                    return true
                }

                navigation_team -> {
                    val fragment = TeamFragment()

                    prefs!!.toSearch = TO_SEARCH_TEAMS

                    // change the main fragment to PrevFragment
                    fragmentController(fragment)

                    return true
                }

                navigation_favourites -> {
                    val fragment = FavouriteFragment()

                    // change the main fragment to FavouriteFragment
                    fragmentController(fragment)

                    return true
                }
            }

            return false
        }

    }*/

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(home_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            home_search -> {
                startActivity<SearchActivity>()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    // Add/Replace fragment in fragment_container
    private fun fragmentController(savedInstanceState: Bundle?, fragment: Fragment) {
        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_container, fragment, fragment.javaClass.simpleName)
                    .commit()
        }
    }

}
