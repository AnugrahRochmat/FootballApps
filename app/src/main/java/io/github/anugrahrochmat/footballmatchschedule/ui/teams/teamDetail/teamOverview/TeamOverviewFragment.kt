package io.github.anugrahrochmat.footballmatchschedule.ui.teams.teamDetail.teamOverview

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.anugrahrochmat.footballmatchschedule.R
import kotlinx.android.synthetic.main.fragment_team_overview.*
import org.jetbrains.anko.support.v4.ctx

/**
 *  Created by Anugrah Rochmat on 12/10/18
 */
class TeamOverviewFragment: Fragment(), TeamOverviewView {
    private lateinit var presenter: TeamOverviewPresenter

    companion object{
        private const val TEAM_OVERVIEW = "TEAM_OVERVIEW"

        fun newInstance(overview: String): TeamOverviewFragment {
            val args = Bundle()
            args.putString(TEAM_OVERVIEW, overview)

            val fragment = TeamOverviewFragment()
            fragment.arguments = args

            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_team_overview, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val overview = arguments!!.getString(TEAM_OVERVIEW)

        presenter = TeamOverviewPresenter(this)
        presenter.loadTeamDesc(overview)
        presenter.onViewAttached()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onViewDestroyed()
    }

    override fun showTeamDesc(overview: String){
        if (!overview.contains("null")){
            tv_overview_team_desc.text = overview
        } else {
            tv_overview_team_desc.text = ctx.getText(R.string.no_desc)
        }

    }
}