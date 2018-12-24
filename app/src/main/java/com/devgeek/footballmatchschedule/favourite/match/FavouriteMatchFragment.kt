package com.devgeek.footballmatchschedule.favourite.match


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout

import com.devgeek.footballmatchschedule.R
import com.devgeek.footballmatchschedule.database.FavouriteMatch
import com.devgeek.footballmatchschedule.database.databaseMatch
import com.devgeek.footballmatchschedule.invisible
import com.devgeek.footballmatchschedule.match.detail.MatchDetailActivity
import com.devgeek.footballmatchschedule.visible
import kotlinx.android.synthetic.main.fragment_favourite.view.*
import kotlinx.android.synthetic.main.fragment_favourite_match.view.*
import org.jetbrains.anko.db.select
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.startActivity

class FavouriteMatchFragment : Fragment() {
    private var favourites: MutableList<FavouriteMatch> = mutableListOf()
    private lateinit var adapter: FavouriteMatchAdapter

    private lateinit var layoutView: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        layoutView = inflater.inflate(R.layout.fragment_favourite_match, container, false)

        adapter = FavouriteMatchAdapter(favourites) {
            startActivity<MatchDetailActivity>(
                    "event_id" to "${it.eventId}",
                    "event_name" to "${it.eventName}"
            )
        }

        layoutView.pb_favourite_match_fragment.visible()

        showFavorite()

        layoutView.rv_favourite_match_fragment.layoutManager = LinearLayoutManager(this.context, LinearLayout.VERTICAL, false)
        layoutView.rv_favourite_match_fragment.adapter = adapter

        layoutView.swipe_refresh_favourite_match.onRefresh {
            favourites.clear()
            showFavorite()
        }

        return layoutView
    }

    private fun showFavorite(){
        context?.databaseMatch?.use {
            layoutView.swipe_refresh_favourite_match.isRefreshing = false
            val result = select(FavouriteMatch.TABLE_FAVOURITE_MATCH)
            val favourite = result.parseList(classParser<FavouriteMatch>())
            favourites.addAll(favourite)
            adapter.notifyDataSetChanged()
            layoutView.pb_favourite_match_fragment.invisible()
        }
    }
}