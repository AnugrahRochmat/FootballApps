package io.github.anugrahrochmat.footballmatchschedule.ui.matches.tabsLayoutMatches

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.anugrahrochmat.footballmatchschedule.R
import kotlinx.android.synthetic.main.fragment_tabs_match_container.*
import kotlinx.android.synthetic.main.fragment_tabs_match_container.view.*

/**
 *  Created by Anugrah Rochmat on 09/10/18
 */
class TabsLayoutFragment: Fragment() {

    companion object{
        fun newInstance(): TabsLayoutFragment {
            val fragment = TabsLayoutFragment()
            return fragment
        }
    }

    // TODO Title Tab View Pager not Fucking Showing

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_tabs_match_container, container, false)

        // Create an adapter that knows which fragment should be shown on each page
        val adapter = TabsViewPagerAdapter(childFragmentManager)

        // Set the adapter onto the view pager
        view.view_pager_match.adapter = adapter
        view.tab_layout_match.setupWithViewPager(view_pager_match)

        return view
    }
}