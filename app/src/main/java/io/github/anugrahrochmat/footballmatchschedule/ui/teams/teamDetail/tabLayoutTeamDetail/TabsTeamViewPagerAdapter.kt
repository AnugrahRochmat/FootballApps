package io.github.anugrahrochmat.footballmatchschedule.ui.teams.teamDetail.tabLayoutTeamDetail

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import io.github.anugrahrochmat.footballmatchschedule.ui.teams.teamDetail.teamOverview.TeamOverviewFragment
import io.github.anugrahrochmat.footballmatchschedule.ui.teams.teamDetail.teamPlayers.TeamPlayersFragment

/**
 *  Created by Anugrah Rochmat on 12/10/18
 */
class TabsTeamViewPagerAdapter(fm: FragmentManager, private val overview: String, private  val teamName: String): FragmentPagerAdapter(fm)
{
    private val OVERVIEW: String = "Overview"
    private val PLAYERS:  String = "Players"
//    private val overview: String = overview
//    private val teamName: String = teamName

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> TeamOverviewFragment.newInstance(overview)
            else -> TeamPlayersFragment.newInstance(teamName)
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> OVERVIEW
            else -> PLAYERS
        }
    }
}