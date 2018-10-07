package io.github.anugrahrochmat.footballmatchschedule.ui.matchActivity.matchSchedule

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import io.github.anugrahrochmat.footballmatchschedule.data.models.MatchSchedule
import io.github.anugrahrochmat.footballmatchschedule.ui.matchActivity.matchDetail.MatchDetailActivity
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.startActivity

class MatchScheduleFragment : Fragment(), MatchScheduleView, AnkoComponent<Context> {
    private lateinit var presenter: MatchSchedulePresenter
    private lateinit var adapter: MatchScheduleAdapter
    private lateinit var rvMatchSchedule: RecyclerView
    private lateinit var progressBar:ProgressBar

    companion object{
        const val rvMatchScheduleID = 1
        const val progressBarID = 2
        const val rlItemContainer = 3
        const val PREV: String = "prev"
        const val NEXT: String = "next"
        const val ID_LEAGUE: String = "4332"
        private const val MATCH_SCHEDULE_STATE = "MATCH_SCHEDULE_STATE"

        fun newInstance(match_schedule_state: String): MatchScheduleFragment {
            val args = Bundle()
            args.putString(MATCH_SCHEDULE_STATE, match_schedule_state)

            val fragment = MatchScheduleFragment()
            fragment.arguments = args

            return fragment
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val matchState = arguments!!.getString(MATCH_SCHEDULE_STATE)

        presenter = MatchSchedulePresenter(this)
        presenter.getMatchSchedule(matchState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return createView(AnkoContext.create(ctx))
    }

    override fun createView(ui: AnkoContext<Context>): View = with(ui) {
        relativeLayout {
            id = rlItemContainer
            lparams(matchParent, matchParent)

            rvMatchSchedule = recyclerView {
                id = rvMatchScheduleID
                lparams(matchParent, wrapContent)
                layoutManager = LinearLayoutManager(ctx)
            }

            progressBar = progressBar {
                id = progressBarID
            }.lparams {
                centerInParent()
            }
        }
    }

    override fun showMatchSchedule(matches: List<MatchSchedule>){
        rvMatchSchedule.adapter = MatchScheduleAdapter(matches) {
            startActivity<MatchDetailActivity>(MatchDetailActivity.EXTRA_MATCH_ID to it.matchId,
                    MatchDetailActivity.EXTRA_HOME_TEAM_NAME to it.homeTeamName,
                    MatchDetailActivity.EXTRA_AWAY_TEAM_NAME to it.awayTeamName)
        }
        adapter.notifyDataSetChanged()
    }

    override fun showLoading(){
        progressBar.visibility = View.VISIBLE
        rvMatchSchedule.visibility = View.GONE
    }

    override fun hideLoading(){
        progressBar.visibility = View.GONE
        rvMatchSchedule.visibility = View.VISIBLE
    }
}