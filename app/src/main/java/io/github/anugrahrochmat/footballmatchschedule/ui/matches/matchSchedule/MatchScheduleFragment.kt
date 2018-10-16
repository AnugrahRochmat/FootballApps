package io.github.anugrahrochmat.footballmatchschedule.ui.matches.matchSchedule

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
import io.github.anugrahrochmat.footballmatchschedule.data.models.MatchSchedule
import io.github.anugrahrochmat.footballmatchschedule.ui.MainActivity
import io.github.anugrahrochmat.footballmatchschedule.ui.matches.matchDetail.MatchDetailActivity
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.startActivity

class MatchScheduleFragment : Fragment(), MatchScheduleView, AnkoComponent<Context> {
    private lateinit var presenter: MatchSchedulePresenter
    private lateinit var adapter: MatchScheduleAdapter
    private lateinit var rvMatchSchedule: RecyclerView
    private lateinit var progressBar:ProgressBar
    private lateinit var spinnerMatch: Spinner

    companion object{
        const val rvMatchScheduleID = 1
        const val progressBarID = 2
        const val rlItemContainer = 3
        const val spinnerMatchID = 4
        const val PREV: String = "prev"
        const val NEXT: String = "next"
        private const val MATCH_SCHEDULE_STATE = "MATCH_SCHEDULE_STATE"
        private const val LEAGUE_ID = "LEAGUE_ID"

        fun newInstance(matchScheduleState: String, leagueId: String): MatchScheduleFragment {
            val args = Bundle()
            args.putString(MATCH_SCHEDULE_STATE, matchScheduleState)
            args.putString(LEAGUE_ID, leagueId)

            val fragment = MatchScheduleFragment()
            fragment.arguments = args

            return fragment
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val matchState = arguments!!.getString(MATCH_SCHEDULE_STATE)
        val leagueId = arguments!!.getString(LEAGUE_ID)

        presenter = MatchSchedulePresenter(this)
        presenter.getMatchSchedule(matchState, leagueId)
        presenter.loadSpinnerFeature(matchState)
        presenter.onViewAttached()
    }

//    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
//        super.onCreateOptionsMenu(menu, inflater)
//
//        activity?.menuInflater?.inflate(R.menu.main_menu, menu)
//        val mSearchMenuItem = menu?.findItem(R.id.searchMenu)
//        val searchView = mSearchMenuItem?.actionView as SearchView
//
//        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                return false
//            }
//
//            override fun onQueryTextChange(searchText: String?): Boolean {
//                presenter.hideSpinnerFeature()
//                presenter.loadSearchFeature(searchText)
//
//                return true
//            }
//        })
//
//        searchView.setOnCloseListener(object : SearchView.OnCloseListener{
//            override fun onClose(): Boolean {
//                presenter.getMatchSchedule(PREV, MainActivity.EXTRA_LEAGUE_SERIE_A)
//                presenter.loadSpinnerFeature(MainActivity.EXTRA_LEAGUE_SERIE_A)
//
//                return false
//            }
//        })
//    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onViewDestroyed()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        setHasOptionsMenu(true)
        return createView(AnkoContext.create(ctx))
    }

    override fun createView(ui: AnkoContext<Context>): View = with(ui) {
        relativeLayout {
            id = rlItemContainer
            lparams(matchParent, matchParent)

            spinnerMatch = spinner {
                id = spinnerMatchID
            }.lparams{
                leftMargin = dip(8)
                rightMargin = dip(8)
                width = matchParent
                height = wrapContent
            }

            rvMatchSchedule = recyclerView {
                id = rvMatchScheduleID
                layoutManager = LinearLayoutManager(ctx)
            }.lparams {
                width = matchParent
                height = wrapContent
                below(spinnerMatchID)
            }

            progressBar = progressBar {
                id = progressBarID
            }.lparams {
                centerInParent()
            }
        }
    }

//    override fun hideSpinner() {
//        spinnerMatch.visibility = View.GONE
//    }

    override fun showSpinner(matchScheduleState: String) {
//        spinnerMatch.visibility = View.VISIBLE
        ArrayAdapter.createFromResource(context, R.array.leagues, R.layout.spinner_style
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
//                    adapter.setDropDownViewResource(R.layout.spinner_style)
            // Apply the adapter to the spinner
            spinnerMatch.adapter = adapter
        }

        spinnerMatch.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parentView: AdapterView<*>, selectedItemView: View, position: Int, id: Long) {
                val selected = parentView.getItemAtPosition(position).toString()
                val context = parentView.context
                val duration = Toast.LENGTH_SHORT

                val toast = Toast.makeText(context, selected, duration)
                toast.show()

                when (position) {
                    0 -> presenter.getMatchSchedule(matchScheduleState, MainActivity.EXTRA_LEAGUE_SERIE_A)
                    1 -> presenter.getMatchSchedule(matchScheduleState, MainActivity.EXTRA_LEAGUE_EPL)
                    2 -> presenter.getMatchSchedule(matchScheduleState, MainActivity.EXTRA_LEAGUE_LA_LIGA)
                    3 -> presenter.getMatchSchedule(matchScheduleState, MainActivity.EXTRA_LEAGUE_BUNDESLIGA)
                    else -> presenter.getMatchSchedule(matchScheduleState, MainActivity.EXTRA_LEAGUE_LIGUE_1)
                }
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {
            }
        }
    }

    override fun showMatchSchedule(matches: List<MatchSchedule>){
        rvMatchSchedule.adapter = MatchScheduleAdapter(matches) {
            startActivity<MatchDetailActivity>(MatchDetailActivity.EXTRA_MATCH_ID to it.matchId,
                    MatchDetailActivity.EXTRA_HOME_TEAM_NAME to it.homeTeamName,
                    MatchDetailActivity.EXTRA_AWAY_TEAM_NAME to it.awayTeamName)
        }
        adapter.notifyDataSetChanged()
    }

    override fun showLoading(){
        progressBar.visibility = View.VISIBLE
        rvMatchSchedule.visibility = View.GONE
    }

    override fun hideLoading(){
        progressBar.visibility = View.GONE
        rvMatchSchedule.visibility = View.VISIBLE
    }
}