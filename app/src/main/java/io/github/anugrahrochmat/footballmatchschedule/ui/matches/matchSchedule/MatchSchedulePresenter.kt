package io.github.anugrahrochmat.footballmatchschedule.ui.matches.matchSchedule

import android.util.Log
import io.github.anugrahrochmat.footballmatchschedule.data.api.ApiClient
import io.github.anugrahrochmat.footballmatchschedule.data.api.ApiInterface
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MatchSchedulePresenter(private val view: MatchScheduleView){

    private val TAG: String = MatchSchedulePresenter::class.java.simpleName

    private var matchScheduleSubscription: Disposable? = null

    fun onViewAttached() {
    }

    fun onViewDestroyed() {
        matchScheduleSubscription?.dispose()
    }

    fun loadSpinnerFeature(scheduleState: String){
        view.showSpinner(scheduleState)
    }

    fun getMatchSchedule(scheduleState: String, leagueID: String){
        val apiServices = ApiClient.client.create(ApiInterface::class.java)
        when (scheduleState) {
            MatchScheduleFragment.PREV -> apiServices.getPreviousSchedules(leagueID)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnNext{ view.showLoading() }
                    .subscribe(
                            {
                                view.hideLoading()
                                view.showMatchSchedule(it.matchSchedules!!)
                            },
                            {
                                error -> Log.e(TAG, error.message)
                            }
                    )
            MatchScheduleFragment.NEXT -> apiServices.getNextSchedules(leagueID)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnNext{ view.showLoading() }
                    .subscribe(
                            {
                                view.hideLoading()
                                view.showMatchSchedule(it.matchSchedules!!)
                            },
                            {
                                error -> Log.e(TAG, error.message)
                            }
                    )
        }
    }
}