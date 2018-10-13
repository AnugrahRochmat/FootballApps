package io.github.anugrahrochmat.footballmatchschedule.data.models

/**
 *  Created by Anugrah Rochmat on 13/10/18
 */
class TeamFavourite (
        val id: Long?,
        val teamID: String?,
        val teamName: String?,
        val teamBadge: String?,
        val teamStadium: String?,
        val teamDescription: String?,
        val teamFormedYear: String?) {

    companion object {
        const val TABLE_TEAM_FAVOURITE: String = "TABLE_TEAM_FAVOURITE"
        const val ID: String = "ID_"
        const val TEAM_ID: String = "TEAM_ID"
        const val TEAM_NAME: String = "TEAM_NAME"
        const val TEAM_BADGE: String = "TEAM_BADGE"
        const val TEAM_STADIUM: String = "TEAM_STADIUM"
        const val TEAM_DESCRIPTION: String = "TEAM_DESCRIPTION"
        const val TEAM_FORMED_YEAR: String = "TEAM_FORMED_YEAR"
    }
}