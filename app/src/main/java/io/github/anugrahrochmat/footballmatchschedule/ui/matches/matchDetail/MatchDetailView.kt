package io.github.anugrahrochmat.footballmatchschedule.ui.matches.matchDetail

import android.content.Context
import io.github.anugrahrochmat.footballmatchschedule.data.models.Favourite
import io.github.anugrahrochmat.footballmatchschedule.data.models.MatchSchedule

interface MatchDetailView {
    fun getContext(): Context
    fun showLoading()
    fun hideLoading()
    fun loadHomeBadge(urlHomeTeamBadge: String)
    fun loadAwayBadge(urlAwayTeamBadge: String)
    fun showMatchDetail(match: MatchSchedule)
    fun showHeader(match: MatchSchedule)
    fun showGoals(match: MatchSchedule)
    fun showCards(match: MatchSchedule)
    fun showLineups(match: MatchSchedule)
    fun showSubs(match: MatchSchedule)
    fun showFavorites(favorites: List<Favourite>)
    fun showFavoriteInserted(rowId: Long)
    fun showFavouriteDeleted(rowAffected: Int)
}