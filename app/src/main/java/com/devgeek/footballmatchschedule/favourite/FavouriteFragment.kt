package com.devgeek.footballmatchschedule.favourite

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.devgeek.footballmatchschedule.R
import com.devgeek.footballmatchschedule.database.FavouriteMatch
import com.devgeek.footballmatchschedule.database.databaseMatch
import com.devgeek.footballmatchschedule.favourite.match.FavouriteMatchAdapter
import com.devgeek.footballmatchschedule.invisible
import com.devgeek.footballmatchschedule.match.MatchPagerAdapter
import com.devgeek.footballmatchschedule.match.detail.MatchDetailActivity
import com.devgeek.footballmatchschedule.visible
import kotlinx.android.synthetic.main.fragment_favourite.view.*
import kotlinx.android.synthetic.main.fragment_match.view.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.startActivity

class FavouriteFragment : Fragment() {
    private lateinit var layoutView: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        layoutView = inflater.inflate(R.layout.fragment_favourite, container, false)

        val fragmentAdapter = FavouritePagerAdapter(childFragmentManager)
        layoutView.viewpager_favourite.adapter = fragmentAdapter

        layoutView.tabs_favourite.setupWithViewPager(layoutView.viewpager_favourite)

        return layoutView
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.clear()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
}