package io.github.anugrahrochmat.footballmatchschedule.data.database

import android.content.Context
import io.github.anugrahrochmat.footballmatchschedule.data.models.Team
import io.github.anugrahrochmat.footballmatchschedule.data.models.TeamFavourite
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select

/**
 *  Created by Anugrah Rochmat on 13/10/18
 */
class TeamFavouriteDBHelper {
    companion object {
        fun insert(context: Context, team: Team) : Long {
            var rowId = 0L
            context.database.use {
                rowId = insert(TeamFavourite.TABLE_TEAM_FAVOURITE,
                        TeamFavourite.TEAM_ID to team.teamId,
                        TeamFavourite.TEAM_NAME to team.teamName,
                        TeamFavourite.TEAM_BADGE to team.teamBadge,
                        TeamFavourite.TEAM_STADIUM to team.teamStadium,
                        TeamFavourite.TEAM_DESCRIPTION to team.teamDescription,
                        TeamFavourite.TEAM_FORMED_YEAR to team.formedYear)
            }
            return rowId
        }

        fun get(context: Context, teamId: String) : List<TeamFavourite> {
            val teamFavourites = ArrayList<TeamFavourite>()
            context.database.use {
                val result = select(TeamFavourite.TABLE_TEAM_FAVOURITE).whereArgs("(TEAM_ID = {id})", "id" to teamId)
                teamFavourites.addAll(result.parseList(classParser<TeamFavourite>()))
            }
            return teamFavourites
        }

        fun delete(context: Context, teamId: String) : Int {
            var rowAffected = 0
            context.database.use {
                rowAffected = delete(TeamFavourite.TABLE_TEAM_FAVOURITE, "(TEAM_ID = {id})", "id" to teamId)
            }
            return rowAffected
        }

    }
}