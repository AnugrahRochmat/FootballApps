package io.github.anugrahrochmat.footballmatchschedule.ui.teams.teamDetail.teamPlayers

import android.util.Log
import io.github.anugrahrochmat.footballmatchschedule.data.api.ApiClient
import io.github.anugrahrochmat.footballmatchschedule.data.api.ApiInterface
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 *  Created by Anugrah Rochmat on 13/10/18
 */
class TeamPlayersPresenter(val view: TeamPlayersView) {

    private val TAG: String = TeamPlayersPresenter::class.java.simpleName
    val apiServices = ApiClient.client.create(ApiInterface::class.java)

    private var teamPlayersSubscription: Disposable? = null

    fun onViewAttached() {
    }

    fun onViewDestroyed() {
        teamPlayersSubscription?.dispose()
    }

    fun getPlayerList(teamName: String) {
        teamPlayersSubscription = apiServices.getPlayersByTeams(teamName)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext { view.showLoading() }
                .subscribe(
                        {
                            view.hideLoading()
                            view.showPlayers(it.players!!)
                        },
                        {
                            error -> Log.e(TAG, error.message)
                        }
                )
    }
}