package io.github.anugrahrochmat.footballmatchschedule.ui.teams.teamDetail

import android.content.Context
import io.github.anugrahrochmat.footballmatchschedule.data.models.Team
import io.github.anugrahrochmat.footballmatchschedule.data.models.TeamFavourite

/**
 *  Created by Anugrah Rochmat on 10/10/18
 */
interface TeamDetailView {
    fun getContext() : Context
    fun showTeamDetail(team: Team)
    fun showTeamFavourites(teamFavourite: List<TeamFavourite>)
    fun showTeamFavoriteInserted(rowId: Long)
    fun showTeamFavouriteDeleted(rowAffected: Int)
}