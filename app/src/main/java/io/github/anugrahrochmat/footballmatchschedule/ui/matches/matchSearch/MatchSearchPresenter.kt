package io.github.anugrahrochmat.footballmatchschedule.ui.matches.matchSearch

import android.util.Log
import io.github.anugrahrochmat.footballmatchschedule.data.api.ApiClient
import io.github.anugrahrochmat.footballmatchschedule.data.api.ApiInterface
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 *  Created by Anugrah Rochmat on 13/10/18
 */
class MatchSearchPresenter(private val view: MatchSearchView) {

    private val TAG: String = MatchSearchPresenter::class.java.simpleName
    private val apiServices = ApiClient.client.create(ApiInterface::class.java)

    private var matchSearchSubscription: Disposable? = null

    fun onViewAttached() {
    }

    fun onViewDestroyed() {
        matchSearchSubscription?.dispose()
    }

    fun loadSearchFeature(searchText: String?){
        matchSearchSubscription = apiServices.getSearchMatch(searchText!!)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext { view.showLoading() }
                .subscribe(
                        {
                            if (it.matchSchedules == null) {
                                view.showNoSearchResult()
                            } else {
                                view.hideLoading()
                                view.showSearchMatch(it.matchSchedules!!)
                            }
                        },
                        {
                            error -> Log.e(TAG, error.message)
                        }
                )
    }
}