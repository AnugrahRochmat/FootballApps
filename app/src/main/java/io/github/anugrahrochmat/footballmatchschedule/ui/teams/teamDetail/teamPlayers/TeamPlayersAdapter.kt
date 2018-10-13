package io.github.anugrahrochmat.footballmatchschedule.ui.teams.teamDetail.teamPlayers

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import io.github.anugrahrochmat.footballmatchschedule.R
import io.github.anugrahrochmat.footballmatchschedule.data.models.Player
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.player_item_list.*

/**
 *  Created by Anugrah Rochmat on 13/10/18
 */
class TeamPlayersAdapter(private val players: List<Player>, private val listener: (Player) -> Unit)
    : RecyclerView.Adapter<PlayerListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.player_item_list, parent, false)
        return PlayerListViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlayerListViewHolder, position: Int) {
        holder.bindItem(players[position], listener)
    }

    override fun getItemCount(): Int = players.size

}

class PlayerListViewHolder(override val containerView: View): RecyclerView.ViewHolder(containerView), LayoutContainer {
//    private val imgBadgeTeam: ImageView = itemView.findViewById(TeamListUI.imgBadgeTeamID)
//    private val tvTeamName: TextView = itemView.findViewById(TeamListUI.tvTeamNameID)

    fun bindItem(player: Player, listener: (Player) -> Unit){
        tv_player_name.text = player.playerName
        tv_player_position.text = player.playerPosition
        Picasso.get().load(player.playerCutOut).into(img_player)

        containerView.setOnClickListener { listener(player) }
    }
}