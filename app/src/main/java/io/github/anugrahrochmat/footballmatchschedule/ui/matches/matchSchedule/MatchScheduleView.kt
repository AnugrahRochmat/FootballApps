package io.github.anugrahrochmat.footballmatchschedule.ui.matches.matchSchedule

import io.github.anugrahrochmat.footballmatchschedule.data.models.MatchSchedule

interface MatchScheduleView {
    fun showLoading()
    fun hideLoading()
    fun showSpinner(matchScheduleState: String)
//    fun hideSpinner()
    fun showMatchSchedule(matches: List<MatchSchedule>)
}