package io.github.anugrahrochmat.footballmatchschedule.ui.favourites.teamFavourites

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import io.github.anugrahrochmat.footballmatchschedule.R
import io.github.anugrahrochmat.footballmatchschedule.data.models.Team
import io.github.anugrahrochmat.footballmatchschedule.data.models.TeamFavourite
import io.github.anugrahrochmat.footballmatchschedule.ui.teams.teamDetail.TeamDetailActivity
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.ctx

/**
 *  Created by Anugrah Rochmat on 13/10/18
 */
class TeamFavouritesFragment: Fragment(), TeamFavouritesView, AnkoComponent<Context> {
    private lateinit var presenter: TeamFavouritesPresenter
    private lateinit var rvTeamFavourites: RecyclerView
    private lateinit var progressBarTeamFav: ProgressBar
    private lateinit var tvNoTeamFavourite: TextView

    companion object{
        const val rvTeamFavouritesID = 1
        const val tvNoTeamFavouriteID = 2
        const val progressBarTeamFavID = 3

        fun newInstance(): TeamFavouritesFragment {
            return TeamFavouritesFragment()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        presenter = TeamFavouritesPresenter(ctx, this)
        presenter.getTeamFavouritesData()
    }

    override fun onResume() {
        super.onResume()
        presenter.getTeamFavouritesData()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return createView(AnkoContext.create(ctx))
    }

    override fun createView(ui: AnkoContext<Context>): View = with(ui) {
        relativeLayout {
            lparams(matchParent, matchParent)

            rvTeamFavourites = recyclerView {
                id = rvTeamFavouritesID
                lparams(matchParent, wrapContent)
                layoutManager = LinearLayoutManager(ctx)
            }

            tvNoTeamFavourite= textView {
                id = tvNoTeamFavouriteID
                text = ctx.getString(R.string.table_empty)
                textColor = Color.RED
                textSize = 18f
                padding = dip(16)
            }.lparams {
                centerInParent()
            }

            progressBarTeamFav = progressBar {
                id = progressBarTeamFavID
            }.lparams {
                centerInParent()
            }
        }
    }

    override fun showTeamFavourites(teams: List<TeamFavourite>){
        rvTeamFavourites.adapter = TeamFavouritesAdapter(teams) {
            val intent = Intent(context, TeamDetailActivity::class.java)
            val team = Team(teamId = it.teamID,
                            teamName = it.teamName,
                            teamStadium = it.teamStadium,
                            teamBadge = it.teamBadge,
                            teamDescription = it.teamDescription)

            intent.putExtra(TeamDetailActivity.EXTRA_TEAM, team)

            startActivity(intent)
        }
    }

    override fun showLoading(){
        progressBarTeamFav.visibility = View.VISIBLE
        rvTeamFavourites.visibility = View.GONE
        tvNoTeamFavourite.visibility = View.GONE
    }

    override fun hideLoading(isFavExist: Boolean){
        progressBarTeamFav.visibility = View.GONE
        if (isFavExist) {
            rvTeamFavourites.visibility = View.VISIBLE
        } else {
            tvNoTeamFavourite.visibility = View.VISIBLE
        }
    }
}