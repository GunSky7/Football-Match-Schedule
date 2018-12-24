package com.devgeek.footballmatchschedule.favourite

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.devgeek.footballmatchschedule.favourite.match.FavouriteMatchFragment
import com.devgeek.footballmatchschedule.favourite.team.FavouriteTeamFragment
import com.devgeek.footballmatchschedule.match.nextmatch.NextFragment
import com.devgeek.footballmatchschedule.match.prevmatch.PrevFragment

class FavouritePagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                FavouriteMatchFragment()
            }

            else -> {
                return FavouriteTeamFragment()
            }
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> {
                "MATCHES"
            }

            else -> {
                return "TEAMS"
            }
        }
    }

}