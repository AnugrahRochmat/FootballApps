package io.github.anugrahrochmat.footballmatchschedule.ui.teams.teamList

import android.util.Log
import io.github.anugrahrochmat.footballmatchschedule.data.api.ApiClient
import io.github.anugrahrochmat.footballmatchschedule.data.api.ApiInterface
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 *  Created by Anugrah Rochmat on 10/10/18
 */
class TeamListPresenter(private val view: TeamListView) {

    private val TAG: String = TeamListPresenter::class.java.simpleName
    private val apiServices = ApiClient.client.create(ApiInterface::class.java)

    private var teamListSubscription: Disposable? = null

    fun onViewAttached() {
    }

    fun onViewDestroyed() {
        teamListSubscription?.dispose()
    }

    fun loadSearchFeature(searchText: String?){
        teamListSubscription = apiServices.getTeams(searchText!!)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext { view.showLoading() }
                .subscribe(
                        {
                            if (it.teams == null) {
                                view.showNoSearchResult()
                            } else {
                                view.hideLoading()
                                view.showTeamList(it.teams!!)
                            }
                        },
                        {
                            error -> Log.e(TAG, error.message)
                        }
                )
    }

    fun loadSpinnerFeature(){
        view.showSpinner()
    }

    fun hideSpinnerFeature(){
        view.hideSpinner()
    }

    fun getTeamList(idLeague: String) {
        teamListSubscription = apiServices.getTeamsByLeague(idLeague)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext { view.showLoading() }
                .subscribe(
                        {
                            view.hideLoading()
                            view.showTeamList(it.teams!!)
                        },
                        {
                            error -> Log.e(TAG, error.message)
                        }
                )
    }
}