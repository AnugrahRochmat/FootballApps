package io.github.anugrahrochmat.footballmatchschedule.ui.teams.teamDetail

import io.github.anugrahrochmat.footballmatchschedule.data.database.TeamFavouriteDBHelper
import io.github.anugrahrochmat.footballmatchschedule.data.models.Team
import io.reactivex.disposables.Disposable
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

/**
 *  Created by Anugrah Rochmat on 10/10/18
 */
class TeamDetailPresenter(private val view: TeamDetailView) {

    private var teamDetailSubscription: Disposable? = null

    fun onViewAttached() {
    }

    fun onViewDestroyed() {
        teamDetailSubscription?.dispose()
    }

    fun loadTeamDetail(team: Team){
        view.showTeamDetail(team)
    }

    fun getTeamFavorites(teamId: String) {
        doAsync {
            val favorites = TeamFavouriteDBHelper.get(view.getContext(), teamId)
            uiThread {
                view.showTeamFavourites(favorites)
            }
        }
    }

    fun insertTeamFavorites(team: Team) {
        doAsync {
            val rowId = TeamFavouriteDBHelper.insert(view.getContext(), team)
            uiThread {
                view.showTeamFavoriteInserted(rowId)
            }
        }
    }

    fun deleteTeamFavorites(teamId: String){
        doAsync {
            val rowAffected = TeamFavouriteDBHelper.delete(view.getContext(), teamId)
            uiThread {
                view.showTeamFavouriteDeleted(rowAffected)
            }
        }
    }
}