package com.devgeek.footballmatchschedule.match

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.devgeek.footballmatchschedule.match.nextmatch.NextFragment
import com.devgeek.footballmatchschedule.match.prevmatch.PrevFragment

class MatchPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                NextFragment()
            }

            else -> {
                PrevFragment()
            }
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> {
                "NEXT"
            }

            else -> {
                "LAST"
            }
        }
    }

}