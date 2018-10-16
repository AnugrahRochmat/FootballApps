package io.github.anugrahrochmat.footballmatchschedule.ui.teams.teamList

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.*
import io.github.anugrahrochmat.footballmatchschedule.R
import io.github.anugrahrochmat.footballmatchschedule.data.models.Team
import io.github.anugrahrochmat.footballmatchschedule.ui.MainActivity
import io.github.anugrahrochmat.footballmatchschedule.ui.teams.teamDetail.TeamDetailActivity
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.ctx

class TeamListFragment : Fragment(), TeamListView, AnkoComponent<Context> {

    private lateinit var presenter: TeamListPresenter
    private lateinit var adapter: TeamListAdapter
    private lateinit var rvTeamListView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var spinnerTeam: Spinner
    private lateinit var tvNoSearchResult: TextView

    companion object{
        const val rvTeamListID = 1
        const val progressBarID = 2
        const val rlItemContainer = 3
        const val spinnerID = 4
        const val tvNoSearchResultID = 5
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

    override fun onPrepareOptionsMenu(menu: Menu?) {
        super.onPrepareOptionsMenu(menu)

        val mSearchMenuItem = menu?.findItem(R.id.searchMenu)
        val searchView = mSearchMenuItem?.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(searchText: String?): Boolean {
                presenter.hideSpinnerFeature()
                presenter.loadSearchFeature(searchText)

                return true
            }
        })

        searchView.setOnCloseListener(object : SearchView.OnCloseListener{
            override fun onClose(): Boolean {
                presenter.getTeamList(MainActivity.EXTRA_LEAGUE_SERIE_A)
                presenter.loadSpinnerFeature()

                return false
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)

        activity?.menuInflater?.inflate(R.menu.main_menu, menu)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onViewDestroyed()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
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

            view {
                backgroundDrawable = ctx.getDrawable(R.drawable.shadow)
            }.lparams{
                width = matchParent
                height = dip(4)
                below(spinnerID)
            }

            tvNoSearchResult = textView {
                id = tvNoSearchResultID
                text = ctx.getString(R.string.search_result_empty)
                textColor = Color.RED
                textSize = 18f
                padding = dip(16)
                visibility = View.GONE
            }.lparams {
                centerInParent()
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
        spinnerTeam.visibility = View.VISIBLE
        ArrayAdapter.createFromResource(context, R.array.leagues, R.layout.spinner_style
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
//             adapter.setDropDownViewResource(R.layout.spinner_style)
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

    override fun hideSpinner() {
        spinnerTeam.visibility = View.GONE
    }

    override fun showTeamList(teams: List<Team>) {
        rvTeamListView.adapter = TeamListAdapter(teams) {
            val intent = Intent(context, TeamDetailActivity::class.java)
            intent.putExtra(TeamDetailActivity.EXTRA_TEAM, it)

            startActivity(intent)
        }
        adapter.notifyDataSetChanged()
    }

    override fun showNoSearchResult(){
        progressBar.visibility = View.GONE
        rvTeamListView.visibility = View.GONE
        tvNoSearchResult.visibility = View.VISIBLE
    }

    override fun showLoading(){
        progressBar.visibility = View.VISIBLE
        rvTeamListView.visibility = View.GONE
        tvNoSearchResult.visibility = View.GONE
    }

    override fun hideLoading(){
        progressBar.visibility = View.GONE
        rvTeamListView.visibility = View.VISIBLE
        tvNoSearchResult.visibility = View.GONE
    }

}
