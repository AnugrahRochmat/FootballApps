package io.github.anugrahrochmat.footballmatchschedule.ui.teams.teamDetail

import io.github.anugrahrochmat.footballmatchschedule.data.models.Team
import io.reactivex.disposables.Disposable

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
}