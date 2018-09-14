package io.github.anugrahrochmat.footballmatchschedule.ui.match_detail

import android.util.Log
import io.github.anugrahrochmat.footballmatchschedule.data.api.ApiClient
import io.github.anugrahrochmat.footballmatchschedule.data.api.ApiInterface
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MatchDetailPresenter(private val view: MatchDetailView) {

    private val TAG: String = MatchDetailPresenter::class.java.simpleName
    private val apiServices = ApiClient.client.create(ApiInterface::class.java)

    fun getMatchDetail(matchId: String?){
        apiServices.getMatchDetail(matchId!!)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext { view.showLoading() }
                .subscribe(
                        {
                            view.hideLoading()
                            view.showMatchDetail(it.matchSchedules!![0])
                        },
                        {
                            error -> Log.e(TAG, error.message)
                        }
                )
    }

    fun getTeamBadges(homeTeamName: String?, awayTeamName: String?) {
        // getHomeBadge
        apiServices.getTeams(homeTeamName!!)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext{ view.showLoading() }
                .subscribe(
                        {
                            view.hideLoading()
                            view.loadHomeBadge(it.teams!![0].teamBadge.toString())
                        },
                        {
                            error -> Log.e(TAG, error.message)
                        }
                )

        // getAwayBadge
        apiServices.getTeams(awayTeamName!!)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext{ view.showLoading() }
                .subscribe(
                        {
                            view.hideLoading()
                            view.loadAwayBadge(it.teams!![0].teamBadge.toString())
                        },
                        {
                            error -> Log.e(TAG, error.message)
                        }
                )

    }
}