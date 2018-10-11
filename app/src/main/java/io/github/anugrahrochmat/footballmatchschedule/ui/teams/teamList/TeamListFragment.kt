package io.github.anugrahrochmat.footballmatchschedule.ui.teams.teamList

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import io.github.anugrahrochmat.footballmatchschedule.R
import io.github.anugrahrochmat.footballmatchschedule.data.models.Team
import io.github.anugrahrochmat.footballmatchschedule.ui.MainActivity
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.ctx

class TeamListFragment : Fragment(), TeamListView, AnkoComponent<Context> {

    private lateinit var presenter: TeamListPresenter
    private lateinit var adapter: TeamListAdapter
    private lateinit var rvTeamListView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var spinnerTeam: Spinner

    companion object{
        const val rvTeamListID = 1
        const val progressBarID = 2
        const val rlItemContainer = 3
        const val spinnerID = 4
        private const val LEAGUE_ID = "LEAGUE_ID"

        fun newInstance(leagueId: String): TeamListFragment {
            val args = Bundle()
            args.putString(TeamListFragment.LEAGUE_ID, leagueId)

            val fragment = TeamListFragment()
            fragment.arguments = args

            return fragment
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val leagueId = arguments!!.getString(TeamListFragment.LEAGUE_ID)

        presenter = TeamListPresenter(this)
        presenter.getTeamList(leagueId)
        presenter.loadSpinnerFeature()
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

            spinnerTeam = spinner {
                id = spinnerID
            }.lparams{
                leftMargin = dip(8)
                rightMargin = dip(8)
                width = matchParent
                height = wrapContent
            }

            rvTeamListView = recyclerView {
                id = rvTeamListID
                layoutManager = LinearLayoutManager(ctx)
            }.lparams{
                width = matchParent
                height = matchParent
                below(spinnerID)
            }

            progressBar = progressBar {
                id = progressBarID
            }.lparams {
                centerInParent()
            }
        }
    }

    override fun showSpinner() {
        ArrayAdapter.createFromResource(context, R.array.leagues, R.layout.spinner_style
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
//                    adapter.setDropDownViewResource(R.layout.spinner_style)
            // Apply the adapter to the spinner
            spinnerTeam.adapter = adapter
        }

        spinnerTeam.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parentView: AdapterView<*>, selectedItemView: View, position: Int, id: Long) {
                val selected = parentView.getItemAtPosition(position).toString()
                val context = parentView.context
                val duration = Toast.LENGTH_SHORT

                val toast = Toast.makeText(context, selected, duration)
                toast.show()

                when (position) {
                    0 -> presenter.getTeamList(MainActivity.EXTRA_LEAGUE_SERIE_A)
                    1 -> presenter.getTeamList(MainActivity.EXTRA_LEAGUE_EPL)
                    2 -> presenter.getTeamList(MainActivity.EXTRA_LEAGUE_LA_LIGA)
                    3 -> presenter.getTeamList(MainActivity.EXTRA_LEAGUE_BUNDESLIGA)
                    else -> presenter.getTeamList(MainActivity.EXTRA_LEAGUE_LIGUE_1)
                }
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {
            }
        }
    }

    override fun showTeamList(teams: List<Team>) {
        rvTeamListView.adapter = TeamListAdapter(teams) {
//            startActivity<MatchDetailActivity>(MatchDetailActivity.EXTRA_MATCH_ID to it.matchId,
//                    MatchDetailActivity.EXTRA_HOME_TEAM_NAME to it.homeTeamName,
//                    MatchDetailActivity.EXTRA_AWAY_TEAM_NAME to it.awayTeamName)
        }
        adapter.notifyDataSetChanged()
    }

    override fun showLoading(){
        progressBar.visibility = View.VISIBLE
        rvTeamListView.visibility = View.GONE
    }

    override fun hideLoading(){
        progressBar.visibility = View.GONE
        rvTeamListView.visibility = View.VISIBLE
    }

}
