package io.github.anugrahrochmat.footballmatchschedule.ui.teams.teamDetail

import io.github.anugrahrochmat.footballmatchschedule.data.models.Team

/**
 *  Created by Anugrah Rochmat on 10/10/18
 */
interface TeamDetailView {
    fun showTeamDetail(team: Team)
}