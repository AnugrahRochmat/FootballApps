package io.github.anugrahrochmat.footballmatchschedule.ui.match_detail

import io.github.anugrahrochmat.footballmatchschedule.data.models.MatchSchedule

interface MatchDetailView {
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
}