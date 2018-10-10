package io.github.anugrahrochmat.footballmatchschedule.ui.matches

import android.support.v7.app.AppCompatActivity

class MatchActivity : AppCompatActivity() {

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(activity_match)
//
//        bottom_navigation.setOnNavigationItemSelectedListener { item ->
//            when (item.itemId) {
//                btn_matches -> {
//                    loadMatchScheduleFragment(savedInstanceState, MatchScheduleFragment.PREV)
//                }
//                btn_teams -> {
//                    loadMatchScheduleFragment(savedInstanceState, MatchScheduleFragment.NEXT)
//                }
//                btn_favourites -> {
//                    loadMatchFavoritesFragment(savedInstanceState)
//                }
//            }
//            true
//        }
//        bottom_navigation.selectedItemId = btn_matches
//    }
//
//    private fun loadMatchScheduleFragment(savedInstanceState: Bundle?, matchState: String) {
//        if (savedInstanceState == null) {
//            supportFragmentManager
//                    .beginTransaction()
//                    .replace(R.id.main_container, MatchScheduleFragment.newInstance(matchState), MatchScheduleFragment::class.java.simpleName)
//                    .commit()
//        }
//    }
//
//    private fun loadMatchFavoritesFragment(savedInstanceState: Bundle?) {
//        if (savedInstanceState == null) {
//            supportFragmentManager
//                    .beginTransaction()
//                    .replace(R.id.main_container, MatchFavouritesFragment.newInstance(), MatchFavouritesFragment::class.java.simpleName)
//                    .commit()
//        }
//    }
}
