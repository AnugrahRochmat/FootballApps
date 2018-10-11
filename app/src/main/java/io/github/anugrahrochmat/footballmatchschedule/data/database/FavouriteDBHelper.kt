package io.github.anugrahrochmat.footballmatchschedule.data.database

import android.content.Context
import io.github.anugrahrochmat.footballmatchschedule.data.models.Favourite
import io.github.anugrahrochmat.footballmatchschedule.data.models.MatchSchedule
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select

class FavouriteDBHelper {

    companion object {
        fun insert(context: Context, match: MatchSchedule, homeTeamBadge: String, awayTeamBadge: String) : Long {
            var rowId = 0L
            context.database.use {
                rowId = insert(Favourite.TABLE_FAVOURITE,
                        Favourite.MATCH_ID to match.matchId,
                        Favourite.HOME_TEAM_NAME to match.homeTeamName,
                        Favourite.HOME_TEAM_SCORE to match.homeTeamScore,
                        Favourite.HOME_TEAM_BADGE to homeTeamBadge,
                        Favourite.AWAY_TEAM_NAME to match.awayTeamName,
                        Favourite.AWAY_TEAM_SCORE to match.awayTeamScore,
                        Favourite.AWAY_TEAM_BADGE to awayTeamBadge,
                        Favourite.DATE_EVENT to match.dateEvent,
                        Favourite.STR_TIME to match.strTime)
            }
            return rowId
        }

        fun get(context: Context, matchId: String) : List<Favourite> {
            val favorites = ArrayList<Favourite>()
            context.database.use {
                val result = select(Favourite.TABLE_FAVOURITE).whereArgs("(MATCH_ID = {id})", "id" to matchId)
                favorites.addAll(result.parseList(classParser<Favourite>()))
            }
            return favorites
        }

        fun delete(context: Context, matchId: String) : Int {
            var rowAffected = 0
            context.database.use {
                rowAffected = delete(Favourite.TABLE_FAVOURITE, "(MATCH_ID = {id})", "id" to matchId)
            }
            return rowAffected
        }

    }
}