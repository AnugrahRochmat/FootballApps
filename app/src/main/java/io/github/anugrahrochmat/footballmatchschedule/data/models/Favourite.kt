package io.github.anugrahrochmat.footballmatchschedule.data.models

data class Favourite(
        val id: Long?,
        val matchID: String?,
        val homeTeamName: String?,
        val homeTeamScore: String?,
        val homeTeamBadge: String?,
        val awayTeamName: String?,
        val awayTeamScore: String?,
        val awayTeamBadge: String?) {

    companion object {
        const val TABLE_FAVOURITE: String = "TABLE_FAVOURITE"
        const val ID: String = "ID_"
        const val MATCH_ID: String = "MATCH_ID"
        const val HOME_TEAM_NAME: String = "HOME_TEAM_NAME"
        const val HOME_TEAM_SCORE: String = "HOME_TEAM_SCORE"
        const val HOME_TEAM_BADGE: String = "HOME_TEAM_BADGE"
        const val AWAY_TEAM_NAME: String = "AWAY_TEAM_NAME"
        const val AWAY_TEAM_SCORE: String = "AWAY_TEAM_SCORE"
        const val AWAY_TEAM_BADGE: String = "AWAY_TEAM_BADGE"
    }
}