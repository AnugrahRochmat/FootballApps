package io.github.anugrahrochmat.footballmatchschedule.ui.matches

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import android.widget.SearchView
import io.github.anugrahrochmat.footballmatchschedule.R
import io.github.anugrahrochmat.footballmatchschedule.ui.matches.matchSearch.MatchSearchFragment
import io.github.anugrahrochmat.footballmatchschedule.ui.matches.tabsLayoutMatches.TabsLayoutFragment

/**
 *  Created by Anugrah Rochmat on 14/10/18
 */
class MatchFragment: Fragment() {

    companion object{
        fun newInstance(): MatchFragment {
            val fragment = MatchFragment()
            return fragment
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        loadTabLayoutMatch()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return inflater!!.inflate(R.layout.fragment_match, container, false)
    }

    override fun onPrepareOptionsMenu(menu: Menu?) {
        super.onPrepareOptionsMenu(menu)

        val searchMenuItem = menu?.findItem(R.id.searchMenu)
        val searchView = searchMenuItem?.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                loadSearchFragment(newText)
                return true
            }
        })

        searchView.setOnCloseListener(object : SearchView.OnCloseListener{
            override fun onClose(): Boolean {
                loadTabLayoutMatch()
                return false
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)

        activity!!.menuInflater.inflate(R.menu.main_menu, menu)
    }

    private fun loadSearchFragment(searchQuery: String?){
        activity!!.supportFragmentManager
                .beginTransaction()
                .replace(R.id.match_container, MatchSearchFragment.newInstance(searchQuery!!), MatchSearchFragment::class.java.simpleName)
                .commit()

    }

    private fun loadTabLayoutMatch(){
        activity!!.supportFragmentManager
                .beginTransaction()
                .replace(R.id.match_container, TabsLayoutFragment.newInstance(), TabsLayoutFragment::class.java.simpleName)
                .commit()
    }
}