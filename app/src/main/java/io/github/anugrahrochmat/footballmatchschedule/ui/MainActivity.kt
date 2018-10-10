package io.github.anugrahrochmat.footballmatchschedule.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.widget.ArrayAdapter
import android.widget.Spinner
import io.github.anugrahrochmat.footballmatchschedule.R
import io.github.anugrahrochmat.footballmatchschedule.ui.matches.matchFavourites.MatchFavouritesFragment
import io.github.anugrahrochmat.footballmatchschedule.ui.matches.matchSchedule.MatchScheduleFragment
import io.github.anugrahrochmat.footballmatchschedule.ui.matches.tabsLayout.TabsLayoutFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.btn_matches -> {
//                    loadMatchScheduleFragment(savedInstanceState, MatchScheduleFragment.PREV)
                    loadTabLayoutMatch(savedInstanceState)
                }
                R.id.btn_teams -> {
//                    loadMatchScheduleFragment(savedInstanceState, MatchScheduleFragment.NEXT)
//                    loadTabLayoutMatch(savedInstanceState)
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



    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        // TODO create spinner feature
        // TODO create search feature

        val spinner: Spinner = menu.findItem(R.id.spinner_item_league).actionView as Spinner
        // Create an ArrayAdapter using the string array and a default spinner layout
                ArrayAdapter.createFromResource(this, R.array.leagues, R.layout.spinner_style
                ).also { adapter ->
                    // Specify the layout to use when the list of choices appears
//                    adapter.setDropDownViewResource(R.layout.spinner_style)
                    // Apply the adapter to the spinner
                    spinner.adapter = adapter
                }

        return true
    }

    private fun loadTabLayoutMatch(savedInstanceState: Bundle?){
        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_container, TabsLayoutFragment.newInstance(), TabsLayoutFragment::class.java.simpleName)
                    .commit()
        }
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
                    .replace(R.id.main_container, MatchFavouritesFragment.newInstance(), MatchFavouritesFragment::class.java.simpleName)
                    .commit()
        }
    }
}
