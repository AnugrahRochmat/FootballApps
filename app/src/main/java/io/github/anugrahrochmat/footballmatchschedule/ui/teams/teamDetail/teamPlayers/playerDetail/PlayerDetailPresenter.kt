package io.github.anugrahrochmat.footballmatchschedule.ui.teams.teamDetail.teamPlayers.playerDetail

import io.github.anugrahrochmat.footballmatchschedule.data.models.Player
import io.reactivex.disposables.Disposable

/**
 *  Created by Anugrah Rochmat on 13/10/18
 */
class PlayerDetailPresenter(val view: PlayerDetailView) {

    private val TAG: String = PlayerDetailPresenter::class.java.simpleName

    private var teamPlayersSubscription: Disposable? = null

    fun onViewAttached() {
    }

    fun onViewDestroyed() {
        teamPlayersSubscription?.dispose()
    }

    fun loadPlayerDetail(player: Player){
        view.showPlayerDetail(player)
    }
}