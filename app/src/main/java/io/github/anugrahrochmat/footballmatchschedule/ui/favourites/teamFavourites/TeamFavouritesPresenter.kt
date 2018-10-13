package io.github.anugrahrochmat.footballmatchschedule.ui.favourites.teamFavourites

import android.content.Context
import io.github.anugrahrochmat.footballmatchschedule.data.database.database
import io.github.anugrahrochmat.footballmatchschedule.data.models.TeamFavourite
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select

/**
 *  Created by Anugrah Rochmat on 13/10/18
 */
class TeamFavouritesPresenter(private val context: Context, private val view: TeamFavouritesView) {
    private var teamFavourites: MutableList<TeamFavourite> = mutableListOf()

    fun getTeamFavouritesData(){
        teamFavourites.clear()

        context.database.use {
            view.showLoading()
            val result = select(TeamFavourite.TABLE_TEAM_FAVOURITE)
            val teamFavourite = result.parseList(classParser<TeamFavourite>())
            teamFavourites.addAll(teamFavourite)

            if (!teamFavourites.isEmpty()){
                view.hideLoading(true)
                view.showTeamFavourites(teamFavourites)
            } else {
                view.hideLoading(false)
            }

        }
    }
}