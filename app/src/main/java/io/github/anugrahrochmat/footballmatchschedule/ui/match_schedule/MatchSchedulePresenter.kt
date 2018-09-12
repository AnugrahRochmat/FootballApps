package io.github.anugrahrochmat.footballmatchschedule.ui.match_schedule

import android.util.Log
import io.github.anugrahrochmat.footballmatchschedule.data.api.ApiClient
import io.github.anugrahrochmat.footballmatchschedule.data.api.ApiInterface
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MatchSchedulePresenter(private val view: MatchScheduleView){

    private val TAG: String = MatchSchedulePresenter::class.java.simpleName

    fun getMatchSchedule(scheduleState: String){
        val apiServices = ApiClient.client.create(ApiInterface::class.java)
        when (scheduleState) {
            MatchScheduleFragment.PREV -> apiServices.getPreviousSchedules(MatchScheduleFragment.ID_LEAGUE)
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
            MatchScheduleFragment.NEXT -> apiServices.getNextSchedules(MatchScheduleFragment.ID_LEAGUE)
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