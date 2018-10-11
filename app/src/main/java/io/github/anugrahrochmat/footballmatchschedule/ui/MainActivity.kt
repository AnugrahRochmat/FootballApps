package io.github.anugrahrochmat.footballmatchschedule.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import io.github.anugrahrochmat.footballmatchschedule.R
import io.github.anugrahrochmat.footballmatchschedule.ui.favourites.matchFavourites.MatchFavouritesFragment
import io.github.anugrahrochmat.footballmatchschedule.ui.matches.tabsLayoutMatches.TabsLayoutFragment
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
                    loadTabLayoutMatch(savedInstanceState)
                }
                R.id.btn_teams -> {
                    loadTeamListFragment(EXTRA_LEAGUE_SERIE_A)
                }
                R.id.btn_favourites -> {
                    loadMatchFavoritesFragment(savedInstanceState)
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

//        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//
//            override fun onItemSelected(parentView: AdapterView<*>, selectedItemView: View, position: Int, id: Long) {
//                val selected = parentView.getItemAtPosition(position).toString()
//                val context = parentView.context
//                val duration = Toast.LENGTH_SHORT
//
//                val toast = Toast.makeText(context, selected, duration)
//                toast.show()
//
//                when (position) {
//                    0 -> loadTeamListFragment(EXTRA_LEAGUE_SERIE_A)
//                    1 -> loadTeamListFragment(EXTRA_LEAGUE_EPL)
//                    2 -> loadTeamListFragment(EXTRA_LEAGUE_LA_LIGA)
//                    3 -> loadTeamListFragment(EXTRA_LEAGUE_BUNDESLIGA)
//                    else -> loadTeamListFragment(EXTRA_LEAGUE_LIGUE_1)
//                }
//            }
//
//            override fun onNothingSelected(parentView: AdapterView<*>) {
//            }
//        }

//        val spinner: Spinner = object : AdapterView.OnItemSelectedListener {

//            spinner.onItemSelectedListener(this) {
//                override fun onItemSelected(parentView: AdapterView<*>, selectedItemView: View, position: Int, id: Long) {
//                    val selected = parentView.getItemAtPosition(position).toString()
//                    val context = parentView.context
//                    val duration = Toast.LENGTH_SHORT
//
//                    val toast = Toast.makeText(context, selected, duration)
//                    toast.show()
//
//                    when (position) {
//                        0 -> loadTeamListFragment(savedInstanceState, EXTRA_LEAGUE_SERIE_A)
//                        1 -> loadTeamListFragment(savedInstanceState, EXTRA_LEAGUE_EPL)
//                        2 -> loadTeamListFragment(savedInstanceState, EXTRA_LEAGUE_LA_LIGA)
//                        3 -> loadTeamListFragment(savedInstanceState, EXTRA_LEAGUE_BUNDESLIGA)
//                        else -> loadTeamListFragment(savedInstanceState, EXTRA_LEAGUE_LIGUE_1)
//                    }
//                }
//
//                override fun onNothingSelected(parentView: AdapterView<*>) {
//                    // your code here
//                }
//            }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        // TODO create spinner feature
        // TODO create search feature

//        val spinner: Spinner = menu.findItem(R.id.spinner_item_league).actionView as Spinner
//
//        ArrayAdapter.createFromResource(this, R.array.leagues, R.layout.spinner_style
//        ).also { adapter ->
//            // Specify the layout to use when the list of choices appears
////                    adapter.setDropDownViewResource(R.layout.spinner_style)
//            // Apply the adapter to the spinner
//            spinner.adapter = adapter
//        }

//        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//
//            override fun onItemSelected(parentView: AdapterView<*>, selectedItemView: View, position: Int, id: Long) {
//                val selected = parentView.getItemAtPosition(position).toString()
//                val context = parentView.context
//                val duration = Toast.LENGTH_SHORT
//
//                val toast = Toast.makeText(context, selected, duration)
//                toast.show()
//
//                when (position) {
//                    0 -> loadTeamListFragment(EXTRA_LEAGUE_SERIE_A)
//                    1 -> loadTeamListFragment(EXTRA_LEAGUE_EPL)
//                    2 -> loadTeamListFragment(EXTRA_LEAGUE_LA_LIGA)
//                    3 -> loadTeamListFragment(EXTRA_LEAGUE_BUNDESLIGA)
//                    else -> loadTeamListFragment(EXTRA_LEAGUE_LIGUE_1)
//                }
//            }
//
//            override fun onNothingSelected(parentView: AdapterView<*>) {
//            }
//    }

        return true
    }

//    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
//        when(item?.itemId) {
//            R.id.spinner -> loadTeamListFragment(savedInstanceState, EXTRA_LEAGUE_LA_LIGA)
//        }
//        //        return super.onOptionsItemSelected(item)
//    }

    private fun loadTabLayoutMatch(savedInstanceState: Bundle?){
        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_container, TabsLayoutFragment.newInstance(), TabsLayoutFragment::class.java.simpleName)
                    .commit()
        }
    }

    private fun loadTeamListFragment(leagueId: String) {
//        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_container, TeamListFragment.newInstance(leagueId), TeamListFragment::class.java.simpleName)
                    .commit()
//        }
    }

    private fun loadMatchFavoritesFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_container, MatchFavouritesFragment.newInstance(), MatchFavouritesFragment::class.java.simpleName)
                    .commit()
        }
    }
}

