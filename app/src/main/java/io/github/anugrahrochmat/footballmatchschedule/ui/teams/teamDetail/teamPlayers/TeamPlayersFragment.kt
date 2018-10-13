package io.github.anugrahrochmat.footballmatchschedule.ui.teams.teamDetail.teamPlayers

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import io.github.anugrahrochmat.footballmatchschedule.data.models.Player
import io.github.anugrahrochmat.footballmatchschedule.ui.teams.teamDetail.teamPlayers.playerDetail.PlayerDetailActivity
import io.github.anugrahrochmat.footballmatchschedule.ui.teams.teamList.TeamListFragment
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.ctx

/**
 *  Created by Anugrah Rochmat on 12/10/18
 */
class TeamPlayersFragment: Fragment(), TeamPlayersView, AnkoComponent<Context>  {

    lateinit var presenter: TeamPlayersPresenter
    lateinit var adapter: TeamPlayersAdapter
    private lateinit var rvTeamPlayerList: RecyclerView
    private lateinit var progressBar: ProgressBar

    companion object{
        const val rvTeamPLayerListID = 1
        const val progressBarPlayerID = 2
        const val rlItemContainer = 3
        private const val TEAM_NAME = "TEAM_NAME"

        fun newInstance(teamName: String): TeamPlayersFragment {
            val args = Bundle()
            args.putString(TEAM_NAME, teamName)

            val fragment = TeamPlayersFragment()
            fragment.arguments = args

            return fragment
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val teamName = arguments!!.getString(TEAM_NAME)

        presenter = TeamPlayersPresenter(this)
        presenter.getPlayerList(teamName)
        presenter.onViewAttached()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onViewDestroyed()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return createView(AnkoContext.create(ctx))
    }

    override fun createView(ui: AnkoContext<Context>): View = with(ui) {
        relativeLayout {
            id = rlItemContainer
            lparams(matchParent, matchParent)

            rvTeamPlayerList = recyclerView {
                id = rvTeamPLayerListID
                layoutManager = LinearLayoutManager(ctx)
            }.lparams{
                width = matchParent
                height = matchParent
                below(TeamListFragment.spinnerID)
            }

            progressBar = progressBar {
                id = progressBarPlayerID
            }.lparams {
                centerInParent()
            }
        }
    }

    override fun showPlayers(playerList: List<Player>) {
        rvTeamPlayerList.adapter = TeamPlayersAdapter(playerList){
            val intent = Intent(context, PlayerDetailActivity::class.java)
            intent.putExtra(PlayerDetailActivity.EXTRA_PLAYER, it)

            startActivity(intent)
        }
        adapter.notifyDataSetChanged()
    }

    override fun showLoading(){
        progressBar.visibility = View.VISIBLE
        rvTeamPlayerList.visibility = View.GONE
    }

    override fun hideLoading(){
        progressBar.visibility = View.GONE
        rvTeamPlayerList.visibility = View.VISIBLE
    }
}