package io.github.anugrahrochmat.footballmatchschedule.ui.matchActivity.matchSchedule

import io.github.anugrahrochmat.footballmatchschedule.data.models.MatchSchedule

interface MatchScheduleView {
    fun showLoading()
    fun hideLoading()
    fun showMatchSchedule(matches: List<MatchSchedule>)
}