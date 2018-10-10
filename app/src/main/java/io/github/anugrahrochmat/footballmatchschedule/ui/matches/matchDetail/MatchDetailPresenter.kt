package io.github.anugrahrochmat.footballmatchschedule.ui.matches.matchDetail

import android.util.Log
import io.github.anugrahrochmat.footballmatchschedule.data.api.ApiClient
import io.github.anugrahrochmat.footballmatchschedule.data.api.ApiInterface
import io.github.anugrahrochmat.footballmatchschedule.data.database.FavouriteDBHelper
import io.github.anugrahrochmat.footballmatchschedule.data.models.MatchSchedule
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MatchDetailPresenter(private val view: MatchDetailView) {

    private val TAG: String = MatchDetailPresenter::class.java.simpleName
    private val apiServices = ApiClient.client.create(ApiInterface::class.java)

    private var matchDetailSubscription: Disposable? = null

    fun onViewAttached() {
    }

    fun onViewDestroyed() {
        matchDetailSubscription?.dispose()
    }

    fun getMatchDetail(matchId: String?){
        matchDetailSubscription = apiServices.getMatchDetail(matchId!!)
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
        matchDetailSubscription = apiServices.getTeams(homeTeamName!!)
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
        matchDetailSubscription = apiServices.getTeams(awayTeamName!!)
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

    fun getFavorites(matchId: String) {
        doAsync {
            val favorites = FavouriteDBHelper.get(view.getContext(), matchId)
            uiThread {
                view.showFavorites(favorites)
            }
        }
    }

    fun insertFavorites(match: MatchSchedule, homeTeamBadge: String, awayTeamBadge: String) {
        doAsync {
            val rowId = FavouriteDBHelper.insert(view.getContext(), match, homeTeamBadge, awayTeamBadge);
            uiThread {
                view.showFavoriteInserted(rowId)
            }
        }
    }

    fun deleteFavorites(matchId: String){
        doAsync {
            val rowAffected = FavouriteDBHelper.delete(view.getContext(), matchId)
            uiThread {
                view.showFavouriteDeleted(rowAffected)
            }
        }
    }
}