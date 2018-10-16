package io.github.anugrahrochmat.footballmatchschedule.ui.matches.matchSearch

import io.github.anugrahrochmat.footballmatchschedule.data.models.MatchSchedule

/**
 *  Created by Anugrah Rochmat on 13/10/18
 */
interface MatchSearchView {
    fun showLoading()
    fun hideLoading()
    fun showSearchMatch(matches: List<MatchSchedule>)
    fun showNoSearchResult()
}