package io.github.anugrahrochmat.footballmatchschedule.ui.favourites.tabLayoutFavourite

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.anugrahrochmat.footballmatchschedule.R
import kotlinx.android.synthetic.main.fragment_tabs_favourites_container.*
import kotlinx.android.synthetic.main.fragment_tabs_favourites_container.view.*

/**
 *  Created by Anugrah Rochmat on 13/10/18
 */
class TabsFavouritesLayoutFragment: Fragment() {

    companion object{
        fun newInstance(): TabsFavouritesLayoutFragment {
            val fragment = TabsFavouritesLayoutFragment()
            return fragment
        }
    }

    // TODO Title Tab View Pager not Fucking Showing

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_tabs_favourites_container, container, false)

        // Create an adapter that knows which fragment should be shown on each page
        val adapter = TabsFavouritesVIewPagerAdapter(childFragmentManager)

        // Set the adapter onto the view pager
        view.view_pager_favourites.adapter = adapter
        view.tab_layout_favourites.setupWithViewPager(view_pager_favourites)

        return view
    }
}