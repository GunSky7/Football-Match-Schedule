package com.devgeek.footballmatchschedule.team.detail

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.devgeek.footballmatchschedule.team.detail.fragment.OverviewFragment
import com.devgeek.footballmatchschedule.player.PlayersFragment

class TeamDetailPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                OverviewFragment()
            }

            else -> {
                return PlayersFragment()
            }
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> {
                "OVERVIEW"
            }

            else -> {
                return "PLAYERS"
            }
        }
    }

}