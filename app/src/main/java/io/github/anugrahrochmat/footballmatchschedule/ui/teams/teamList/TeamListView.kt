package io.github.anugrahrochmat.footballmatchschedule.ui.teams.teamList

import io.github.anugrahrochmat.footballmatchschedule.data.models.Team

/**
 *  Created by Anugrah Rochmat on 10/10/18
 */
interface TeamListView {
    fun showLoading()
    fun hideLoading()
    fun showSpinner()
    fun showTeamList(teams: List<Team>)
}