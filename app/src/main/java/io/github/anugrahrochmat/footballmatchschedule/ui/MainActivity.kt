package io.github.anugrahrochmat.footballmatchschedule.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.github.anugrahrochmat.footballmatchschedule.R
import io.github.anugrahrochmat.footballmatchschedule.R.id.*
import io.github.anugrahrochmat.footballmatchschedule.R.layout.activity_main
import io.github.anugrahrochmat.footballmatchschedule.ui.match_favourites.MatchFavouritesFragment
import io.github.anugrahrochmat.footballmatchschedule.ui.match_schedule.MatchScheduleFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_main)

        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                btn_last_match -> {
                    loadMatchScheduleFragment(savedInstanceState, MatchScheduleFragment.PREV)
                }
                btn_next_match -> {
                    loadMatchScheduleFragment(savedInstanceState, MatchScheduleFragment.NEXT)
                }
                btn_favourites -> {
                    loadMatchFavoritesFragment(savedInstanceState)
                }
            }
            true
        }
        bottom_navigation.selectedItemId = btn_last_match
    }

    private fun loadMatchScheduleFragment(savedInstanceState: Bundle?, matchState: String) {
        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_container, MatchScheduleFragment.newInstance(matchState), MatchScheduleFragment::class.java.simpleName)
                    .commit()
        }
    }

    private fun loadMatchFavoritesFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_container, MatchFavouritesFragment(), MatchFavouritesFragment::class.java.simpleName)
                    .commit()
        }
    }
}
