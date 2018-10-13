package io.github.anugrahrochmat.footballmatchschedule.ui.teams.teamDetail.tabLayoutTeamDetail

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.anugrahrochmat.footballmatchschedule.R
import kotlinx.android.synthetic.main.fragment_tabs_match_container.*
import kotlinx.android.synthetic.main.fragment_tabs_match_container.view.*

/**
 *  Created by Anugrah Rochmat on 12/10/18
 */
class TabsLayoutTeam: Fragment() {

    companion object{
        private const val TEAM_OVERVIEW = "TEAM_OVERVIEW"
        private const val TEAM_NAME = "TEAM_NAME"

        fun newInstance(overview: String, teamName: String): TabsLayoutTeam {
            val args = Bundle()
            args.putString(TEAM_OVERVIEW, overview)
            args.putString(TEAM_NAME, teamName)

            val fragment = TabsLayoutTeam()
            fragment.arguments = args

            return fragment
        }
    }

    // TODO Title Tab View Pager not Fucking Showing

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_tabs_match_container, container, false)

        val overview = arguments!!.getString(TEAM_OVERVIEW)
        val teamName = arguments!!.getString(TEAM_NAME)
        // Create an adapter that knows which fragment should be shown on each page
        val adapter = TabsTeamViewPagerAdapter(childFragmentManager, overview, teamName)

        // Set the adapter onto the view pager
        view.view_pager_match.adapter = adapter
        view.tab_layout_match.setupWithViewPager(view_pager_match)

        return view
    }
}