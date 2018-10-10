package io.github.anugrahrochmat.footballmatchschedule.ui.matches.tabsLayout

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import io.github.anugrahrochmat.footballmatchschedule.ui.matches.matchSchedule.MatchScheduleFragment

/**
 *  Created by Anugrah Rochmat on 09/10/18
 */
class TabsViewPagerAdapter(fm: FragmentManager): FragmentPagerAdapter(fm){

    private val LAST_MATCH:String = "Last Match"
    private val NEXT_MATCH:String = "Next Match"

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> MatchScheduleFragment.newInstance(MatchScheduleFragment.PREV)
            else -> MatchScheduleFragment.newInstance(MatchScheduleFragment.NEXT)
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> LAST_MATCH
            else -> NEXT_MATCH
        }
    }

}