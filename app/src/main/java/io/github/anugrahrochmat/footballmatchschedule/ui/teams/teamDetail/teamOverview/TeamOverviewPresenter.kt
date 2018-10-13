package io.github.anugrahrochmat.footballmatchschedule.ui.teams.teamDetail.teamOverview

import io.reactivex.disposables.Disposable

/**
 *  Created by Anugrah Rochmat on 12/10/18
 */
class TeamOverviewPresenter(private val view: TeamOverviewView) {

    private var teamOverviewSubscription: Disposable? = null

    fun onViewAttached() {
    }

    fun onViewDestroyed() {
        teamOverviewSubscription?.dispose()
    }

    fun loadTeamDesc(overview: String){
        view.showTeamDesc(overview)
    }
}