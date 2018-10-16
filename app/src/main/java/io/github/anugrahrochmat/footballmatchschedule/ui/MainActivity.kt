package io.github.anugrahrochmat.footballmatchschedule.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.github.anugrahrochmat.footballmatchschedule.R
import io.github.anugrahrochmat.footballmatchschedule.ui.favourites.tabLayoutFavourite.TabsFavouritesLayoutFragment
import io.github.anugrahrochmat.footballmatchschedule.ui.matches.MatchFragment
import io.github.anugrahrochmat.footballmatchschedule.ui.teams.teamList.TeamListFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_LEAGUE_SERIE_A = "4332"
        const val EXTRA_LEAGUE_EPL = "4328"
        const val EXTRA_LEAGUE_LA_LIGA = "4335"
        const val EXTRA_LEAGUE_BUNDESLIGA = "4331"
        const val EXTRA_LEAGUE_LIGUE_1 = "4334"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.btn_matches -> {
                    loadMatchFragment(savedInstanceState)
                }
                R.id.btn_teams -> {
                    loadTeamListFragment(EXTRA_LEAGUE_SERIE_A)
                }
                R.id.btn_favourites -> {
                    loadFavoritesFragment(savedInstanceState)
                }
            }
            true
        }
        bottom_navigation.selectedItemId = R.id.btn_matches

        /**
         * Stetho for database sqlite testing
         */
//        // Create an InitializerBuilder
//        val initializerBuilder = Stetho.newInitializerBuilder(this)
//        // Enable Chrome DevTools
//        initializerBuilder.enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
//        // Initialize Stetho with the Initializer
//        Stetho.initialize(initializerBuilder.build())
    }

    private fun loadMatchFragment(savedInstanceState: Bundle?){
        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_container, MatchFragment.newInstance(), MatchFragment::class.java.simpleName)
                    .commit()
        }
    }

    private fun loadTeamListFragment(leagueId: String) {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_container, TeamListFragment.newInstance(leagueId), TeamListFragment::class.java.simpleName)
                .commit()
    }

    private fun loadFavoritesFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_container, TabsFavouritesLayoutFragment.newInstance(), TabsFavouritesLayoutFragment::class.java.simpleName)
                    .commit()
        }
    }
}

