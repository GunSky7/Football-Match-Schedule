package com.devgeek.footballmatchschedule.team.detail.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.devgeek.footballmatchschedule.R
import com.devgeek.footballmatchschedule.api.ApiRepository
import com.devgeek.footballmatchschedule.invisible
import com.devgeek.footballmatchschedule.model.Team
import com.devgeek.footballmatchschedule.presenter.TeamPresenter
import com.devgeek.footballmatchschedule.view.TeamView
import com.devgeek.footballmatchschedule.visible
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_overview.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class OverviewFragment : Fragment(), TeamView {
    private lateinit var layoutView: View

    private lateinit var presenter: TeamPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        layoutView =  inflater.inflate(R.layout.fragment_overview, container, false)

        val intent = activity!!.intent
        val id = intent!!.getStringExtra("team_id")

        val request = ApiRepository()
        val gson = Gson()

        presenter = TeamPresenter(this, request, gson)
        presenter.getTeamDetail(id)

        return layoutView
    }

    override fun showLoading() {
        layoutView.pb_detail_overview.visible()
    }

    override fun hideLoading() {
        layoutView.pb_detail_overview.invisible()
    }

    override fun showTeamList(data: List<Team>) {
        layoutView.tv_detail_overview.text = data[0].teamDescription
    }


}
