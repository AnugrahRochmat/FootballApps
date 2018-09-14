package io.github.anugrahrochmat.footballmatchschedule.ui.match_favourites

import io.github.anugrahrochmat.footballmatchschedule.data.models.Favourite

interface MatchFavouritesView {
    fun showLoading()
    fun hideLoading(isFavExist: Boolean)
    fun showMatchFavourites(matches: List<Favourite>)
}