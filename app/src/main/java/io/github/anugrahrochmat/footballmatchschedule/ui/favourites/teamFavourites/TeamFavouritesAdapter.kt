package io.github.anugrahrochmat.footballmatchschedule.ui.favourites.teamFavourites

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import io.github.anugrahrochmat.footballmatchschedule.data.models.TeamFavourite
import io.github.anugrahrochmat.footballmatchschedule.ui.teams.teamList.TeamListUI
import kotlinx.android.extensions.LayoutContainer
import org.jetbrains.anko.AnkoContext

/**
 *  Created by Anugrah Rochmat on 13/10/18
 */
class TeamFavouritesAdapter(private val favTeams: List<TeamFavourite>, private val listener: (TeamFavourite) -> Unit)
    : RecyclerView.Adapter<TeamFavouritesViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamFavouritesViewHolder {
        return TeamFavouritesViewHolder(TeamListUI().createView(AnkoContext.create(parent.context, parent)))
    }

    override fun onBindViewHolder(holder: TeamFavouritesViewHolder, position: Int) {
        holder.bindItem(favTeams[position], listener)
    }

    override fun getItemCount(): Int = favTeams.size

}

class TeamFavouritesViewHolder(override val containerView: View): RecyclerView.ViewHolder(containerView), LayoutContainer {
    private val imgBadgeTeam: ImageView = itemView.findViewById(TeamListUI.imgBadgeTeamID)
    private val tvTeamName: TextView = itemView.findViewById(TeamListUI.tvTeamNameID)

    fun bindItem(teamFavourite: TeamFavourite, listener: (TeamFavourite) -> Unit){
        tvTeamName.text = teamFavourite.teamName
        Picasso.get().load(teamFavourite.teamBadge).into(imgBadgeTeam)

        containerView.setOnClickListener { listener(teamFavourite) }
    }
}