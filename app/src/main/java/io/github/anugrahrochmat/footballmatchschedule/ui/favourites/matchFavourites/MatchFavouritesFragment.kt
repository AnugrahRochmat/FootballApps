package io.github.anugrahrochmat.footballmatchschedule.ui.favourites.matchFavourites

import android.content.Context
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
import io.github.anugrahrochmat.footballmatchschedule.data.models.Favourite
import io.github.anugrahrochmat.footballmatchschedule.ui.matches.matchDetail.MatchDetailActivity
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.startActivity

class MatchFavouritesFragment : Fragment(), MatchFavouritesView, AnkoComponent<Context> {
    private lateinit var presenter: MatchFavouritesPresenter
    private lateinit var adapter: MatchFavouritesAdapter
    private lateinit var rvMatchFavourites: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var tvNoFavourite: TextView

    companion object{
        const val rvMatchFavouritesID = 1
        const val tvNoFavouriteID = 2
        const val progressBarFavID = 3

        fun newInstance(): MatchFavouritesFragment {
            return MatchFavouritesFragment()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        presenter = MatchFavouritesPresenter(ctx, this)
        presenter.getFavouritesData()
    }

    override fun onResume() {
        super.onResume()
        presenter.getFavouritesData()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return createView(AnkoContext.create(ctx))
    }

    override fun createView(ui: AnkoContext<Context>): View = with(ui) {
        relativeLayout {
            lparams(matchParent, matchParent)

            rvMatchFavourites = recyclerView {
                id = rvMatchFavouritesID
                lparams(matchParent, wrapContent)
                layoutManager = LinearLayoutManager(ctx)
            }

            tvNoFavourite = textView {
                id = tvNoFavouriteID
                text = ctx.getString(R.string.table_empty)
                textColor = Color.RED
                textSize = 18f
                padding = dip(16)
            }.lparams {
                centerInParent()
            }

            progressBar = progressBar {
                id = progressBarFavID
            }.lparams {
                centerInParent()
            }
        }
    }

    override fun showMatchFavourites(matches: List<Favourite>){
        rvMatchFavourites.adapter = MatchFavouritesAdapter(matches) {
            startActivity<MatchDetailActivity>(MatchDetailActivity.EXTRA_MATCH_ID to it.matchID,
                    MatchDetailActivity.EXTRA_HOME_TEAM_NAME to it.homeTeamName,
                    MatchDetailActivity.EXTRA_AWAY_TEAM_NAME to it.awayTeamName)
        }
    }

    override fun showLoading(){
        progressBar.visibility = View.VISIBLE
        rvMatchFavourites.visibility = View.GONE
        tvNoFavourite.visibility = View.GONE
    }

    override fun hideLoading(isFavExist: Boolean){
        progressBar.visibility = View.GONE
        if (isFavExist) {
            rvMatchFavourites.visibility = View.VISIBLE
        } else {
            tvNoFavourite.visibility = View.VISIBLE
        }
    }
}