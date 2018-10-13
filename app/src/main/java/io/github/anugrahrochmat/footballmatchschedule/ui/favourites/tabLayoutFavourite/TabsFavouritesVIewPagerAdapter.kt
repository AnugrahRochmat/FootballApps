package io.github.anugrahrochmat.footballmatchschedule.ui.favourites.tabLayoutFavourite

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import io.github.anugrahrochmat.footballmatchschedule.ui.favourites.matchFavourites.MatchFavouritesFragment
import io.github.anugrahrochmat.footballmatchschedule.ui.favourites.teamFavourites.TeamFavouritesFragment

/**
 *  Created by Anugrah Rochmat on 13/10/18
 */
class TabsFavouritesVIewPagerAdapter(fm: FragmentManager): FragmentPagerAdapter(fm) {

    private val MATCHES:String = "Matches"
    private val TEAMS:String = "Teams"

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> MatchFavouritesFragment.newInstance()
            else -> TeamFavouritesFragment.newInstance()
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> MATCHES
            else -> TEAMS
        }
    }
}