package com.devgeek.footballmatchschedule.match.nextmatch


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import com.devgeek.footballmatchschedule.*
import com.devgeek.footballmatchschedule.api.ApiRepository
import com.devgeek.footballmatchschedule.match.detail.MatchDetailActivity
import com.devgeek.footballmatchschedule.model.League
import com.devgeek.footballmatchschedule.model.Match
import com.devgeek.footballmatchschedule.presenter.MatchPresenter
import com.devgeek.footballmatchschedule.presenter.SpinnerPresenter
import com.devgeek.footballmatchschedule.view.MatchView
import com.devgeek.footballmatchschedule.view.SpinnerView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_next.view.*
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.startActivity

class NextFragment : Fragment(), MatchView, SpinnerView {
    private var matchs: MutableList<Match> = mutableListOf()
    private var league: ArrayList<League> = arrayListOf()
    private var leagueName: ArrayList<String> = arrayListOf()

    private lateinit var presenterMatch: MatchPresenter
    private lateinit var presenterSpinner: SpinnerPresenter
    private lateinit var adapter: NextMatchAdapter
    private lateinit var spinnerAdapter: ArrayAdapter<String>
    private lateinit var layoutView: View
    private lateinit var leagueId: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        layoutView = inflater.inflate(R.layout.fragment_next, container, false)

        val request = ApiRepository()
        val gson = Gson()

        presenterMatch = MatchPresenter(this, request, gson)
        presenterSpinner = SpinnerPresenter(this, request, gson)

        // Initialize spinner
        presenterSpinner.getAllLeague()

        spinnerAdapter = ArrayAdapter(layoutView.context, android.R.layout.simple_spinner_dropdown_item, leagueName)
        layoutView.spinner_next.adapter = spinnerAdapter

        layoutView.spinner_next.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                leagueId = league[position].leagueId.toString()

                presenterMatch.getNextMatchList(leagueId)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        adapter = NextMatchAdapter(matchs) {
            startActivity<MatchDetailActivity>(
                    "event_id" to "${it.eventId}",
                    "event_name" to "${it.eventName}"
            )
        }

        layoutView.rv_next_fragment.layoutManager = LinearLayoutManager(this.context, LinearLayout.VERTICAL, false)
        layoutView.rv_next_fragment.adapter = adapter

        adapter.notifyDataSetChanged()

        layoutView.swipe_refresh_next.onRefresh { presenterMatch.getNextMatchList(leagueId) }

        return layoutView
    }

    override fun showLoading() {
        layoutView.pb_next_fragment.visible()
    }

    override fun hideLoading() {
        layoutView.pb_next_fragment.invisible()
    }

    override fun showMatchList(data: List<Match>) {
        layoutView.swipe_refresh_next.isRefreshing = false
        matchs.clear()
        matchs.addAll(data)
        adapter.notifyDataSetChanged()
    }

    override fun showLeagueList(data: ArrayList<League>) {
        league.clear()
        league.addAll(data)

        leagueName.addAll(data.map { it.leagueName.toString() })
//        Log.d("league", league.toString())

        spinnerAdapter.notifyDataSetChanged()
    }
}
