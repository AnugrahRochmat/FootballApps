package io.github.anugrahrochmat.footballmatchschedule.ui.matches.matchSearch

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import io.github.anugrahrochmat.footballmatchschedule.R
import io.github.anugrahrochmat.footballmatchschedule.data.models.MatchSchedule
import io.github.anugrahrochmat.footballmatchschedule.ui.matches.matchDetail.MatchDetailActivity
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.startActivity

/**
 *  Created by Anugrah Rochmat on 13/10/18
 */
class MatchSearchFragment: Fragment(), MatchSearchView, AnkoComponent<Context> {

    private lateinit var presenter: MatchSearchPresenter
    private lateinit var rvSearchMatch: RecyclerView
    private lateinit var progressBarSearchMatch: ProgressBar
    private lateinit var tvNoSearchMatchResult: TextView

    companion object{
        const val rvSearchMatchID = 1
        const val progressBarSearchMatchID = 2
        const val tvNoSearchMatchResultID = 3
        private const val SEARCH_MATCH_QUERY = "SEARCH_MATCH_QUERY"

        fun newInstance(searchMatchQuery: String): MatchSearchFragment {
            val args = Bundle()
            args.putString(SEARCH_MATCH_QUERY, searchMatchQuery)

            val fragment = MatchSearchFragment()
            fragment.arguments = args

            return fragment
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val searchMatchQuery = arguments!!.getString(SEARCH_MATCH_QUERY)

        presenter = MatchSearchPresenter(this)
        presenter.loadSearchFeature(searchMatchQuery)

        presenter.onViewAttached()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onViewDestroyed()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return createView(AnkoContext.create(ctx))
    }

    override fun createView(ui: AnkoContext<Context>): View = with(ui) {
        relativeLayout {
            lparams(matchParent, matchParent)

            rvSearchMatch = recyclerView {
                id = rvSearchMatchID
                lparams(matchParent, wrapContent)
                layoutManager = LinearLayoutManager(ctx)
            }

            tvNoSearchMatchResult = textView {
                id = tvNoSearchMatchResultID
                text = ctx.getString(R.string.search_result_empty)
                textColor = Color.RED
                textSize = 18f
                padding = dip(16)
                visibility = View.GONE
            }.lparams {
                centerInParent()
            }

            progressBarSearchMatch = progressBar {
                id = progressBarSearchMatchID
            }.lparams {
                centerInParent()
            }
        }
    }

    override fun showSearchMatch(matches: List<MatchSchedule>){
        rvSearchMatch.adapter = MatchSearchAdapter(matches) {
            startActivity<MatchDetailActivity>(MatchDetailActivity.EXTRA_MATCH_ID to it.matchId,
                    MatchDetailActivity.EXTRA_HOME_TEAM_NAME to it.homeTeamName,
                    MatchDetailActivity.EXTRA_AWAY_TEAM_NAME to it.awayTeamName)
        }
    }

    override fun showNoSearchResult() {
        progressBarSearchMatch.visibility = View.GONE
        rvSearchMatch.visibility = View.GONE
        tvNoSearchMatchResult.visibility = View.VISIBLE
    }

    override fun showLoading(){
        progressBarSearchMatch.visibility = View.VISIBLE
        rvSearchMatch.visibility = View.GONE
        tvNoSearchMatchResult.visibility = View.GONE
    }

    override fun hideLoading(){
        progressBarSearchMatch.visibility = View.GONE
        rvSearchMatch.visibility = View.VISIBLE
        tvNoSearchMatchResult.visibility = View.GONE
    }
}