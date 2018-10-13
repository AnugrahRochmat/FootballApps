package io.github.anugrahrochmat.footballmatchschedule.ui.teams.teamDetail.teamPlayers.playerDetail

import io.github.anugrahrochmat.footballmatchschedule.data.models.Player

/**
 *  Created by Anugrah Rochmat on 13/10/18
 */
interface PlayerDetailView {
    fun showPlayerDetail(player: Player)
}