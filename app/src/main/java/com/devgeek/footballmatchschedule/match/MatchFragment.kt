package com.devgeek.footballmatchschedule.match


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup

import com.devgeek.footballmatchschedule.R
import com.devgeek.footballmatchschedule.R.id.home
import com.devgeek.footballmatchschedule.R.id.home_search
import com.devgeek.footballmatchschedule.search.SearchActivity
import kotlinx.android.synthetic.main.fragment_match.*
import kotlinx.android.synthetic.main.fragment_match.view.*
import org.jetbrains.anko.support.v4.startActivity

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class MatchFragment : Fragment() {
    private lateinit var layoutView: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        layoutView = inflater.inflate(R.layout.fragment_match, container, false)

        val fragmentAdapter = MatchPagerAdapter(childFragmentManager)
        layoutView.viewpager_home.adapter = fragmentAdapter

        layoutView.tabs_home.setupWithViewPager(layoutView.viewpager_home)

        return layoutView
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                activity!!.finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}
