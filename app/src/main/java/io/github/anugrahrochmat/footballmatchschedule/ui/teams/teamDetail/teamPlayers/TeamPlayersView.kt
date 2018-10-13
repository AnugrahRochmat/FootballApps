package io.github.anugrahrochmat.footballmatchschedule.ui.teams.teamDetail.teamPlayers

import io.github.anugrahrochmat.footballmatchschedule.data.models.Player

/**
 *  Created by Anugrah Rochmat on 13/10/18
 */
interface TeamPlayersView {
    fun showPlayers(player: List<Player>)
    fun showLoading()
    fun hideLoading()
}