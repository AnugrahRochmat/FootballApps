package io.github.anugrahrochmat.footballmatchschedule.ui.teams.teamList

import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import io.github.anugrahrochmat.footballmatchschedule.R
import io.github.anugrahrochmat.footballmatchschedule.data.models.Team
import kotlinx.android.extensions.LayoutContainer
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView

/**
 *  Created by Anugrah Rochmat on 10/10/18
 */
class TeamListAdapter(private val teams: List<Team>, private val listener: (Team) -> Unit)
    : RecyclerView.Adapter<TeamListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamListViewHolder {
        return TeamListViewHolder(TeamListUI().createView(AnkoContext.create(parent.context, parent)))
    }

    override fun onBindViewHolder(holder: TeamListViewHolder, position: Int) {
        holder.bindItem(teams[position], listener)
    }

    override fun getItemCount(): Int = teams.size
}

class TeamListUI : AnkoComponent<ViewGroup> {
    companion object {
        const val imgBadgeTeamID = 1
        const val tvTeamNameID = 2
    }

    override fun createView(ui: AnkoContext<ViewGroup>) = with(ui) {
        cardView {
            lparams(matchParent, wrapContent)

            linearLayout {
                lparams(matchParent, wrapContent)
                padding = dip(8)
                gravity = Gravity.LEFT

                // Team
                linearLayout {
                    lparams(dip(170), wrapContent)
                    gravity = Gravity.LEFT

                    imageView {
                        id = imgBadgeTeamID
                        // setImageResource(R.drawable.img_acm)
                    }.lparams(dip(50), dip(50)){
                        leftMargin = dip(8)
                        rightMargin = dip(5)
                        topMargin = dip(8)
                        bottomMargin = dip(8)
                    }

                    textView {
                        id = tvTeamNameID
                        text = ctx.getString(R.string.app_name)
                        textSize = 16f
                        minLines = 2
                        gravity = Gravity.CENTER
                        ellipsize = TextUtils.TruncateAt.END
                    }.lparams(wrapContent, wrapContent) {
                        gravity = Gravity.CENTER
                    }
                }
            }
            view {
                backgroundDrawable = ctx.getDrawable(R.drawable.shadow)
            }.lparams(matchParent, dip(4))
        }
    }
}

class TeamListViewHolder(override val containerView: View): RecyclerView.ViewHolder(containerView), LayoutContainer {
    private val imgBadgeTeam: ImageView = itemView.findViewById(TeamListUI.imgBadgeTeamID)
    private val tvTeamName: TextView = itemView.findViewById(TeamListUI.tvTeamNameID)

    fun bindItem(team: Team, listener: (Team) -> Unit){
        tvTeamName.text = team.teamName
        Picasso.get().load(team.teamBadge).into(imgBadgeTeam)

        containerView.setOnClickListener { listener(team) }
    }
}