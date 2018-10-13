package io.github.anugrahrochmat.footballmatchschedule.ui.favourites.teamFavourites

import io.github.anugrahrochmat.footballmatchschedule.data.models.TeamFavourite

/**
 *  Created by Anugrah Rochmat on 13/10/18
 */
interface TeamFavouritesView {
    fun showLoading()
    fun hideLoading(isFavExist: Boolean)
    fun showTeamFavourites(teams: List<TeamFavourite>)
}