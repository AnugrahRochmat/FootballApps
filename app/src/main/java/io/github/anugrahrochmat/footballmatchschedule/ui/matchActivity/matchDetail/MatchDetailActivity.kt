package io.github.anugrahrochmat.footballmatchschedule.ui.matchActivity.matchDetail

import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.squareup.picasso.Picasso
import io.github.anugrahrochmat.footballmatchschedule.R
import io.github.anugrahrochmat.footballmatchschedule.R.drawable.ic_round_favorite_24px
import io.github.anugrahrochmat.footballmatchschedule.R.drawable.ic_round_favorite_border_24px
import io.github.anugrahrochmat.footballmatchschedule.R.id.add_favourite
import io.github.anugrahrochmat.footballmatchschedule.R.layout.activity_match_detail
import io.github.anugrahrochmat.footballmatchschedule.R.menu.detail_menu
import io.github.anugrahrochmat.footballmatchschedule.data.database.database
import io.github.anugrahrochmat.footballmatchschedule.data.models.Favourite
import io.github.anugrahrochmat.footballmatchschedule.data.models.MatchSchedule
import kotlinx.android.synthetic.main.activity_match_detail.*
import org.jetbrains.anko.ctx
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select

class MatchDetailActivity : AppCompatActivity(), MatchDetailView {
    private lateinit var presenter: MatchDetailPresenter
    private lateinit var match: MatchSchedule
    private lateinit var urlHomeTeamBadge: String
    private lateinit var urlAwayTeamBadge: String
    private lateinit var matchId: String
    private lateinit var homeTeamName: String
    private lateinit var awayTeamName: String
    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_match_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = ctx.getString(R.string.match_detail)

        matchId = intent.getSerializableExtra("match").toString()
        homeTeamName = intent.getSerializableExtra("homeTeamName").toString()
        awayTeamName = intent.getSerializableExtra("awayTeamName").toString()

        favouriteState()
        presenter = MatchDetailPresenter(this)
        presenter.getMatchDetail(matchId)
        presenter.getTeamBadges(homeTeamName, awayTeamName)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(detail_menu, menu)
        menuItem = menu
        setFavourite()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home -> {
                finish()
                true
            }
            add_favourite -> {
                if (isFavorite) {
                    removeFromFavourite()
                } else {
                    addFavourite()
                }
                isFavorite = !isFavorite
                setFavourite()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun favouriteState(){
        database.use {
            val result = select(Favourite.TABLE_FAVOURITE).whereArgs("(MATCH_ID = {id})", "id" to matchId)
            val favourite = result.parseList(classParser<Favourite>())
            if (!favourite.isEmpty()) isFavorite = true
        }
    }

    private fun setFavourite() {
        if (isFavorite) {
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_round_favorite_24px)
        }
        else {
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_round_favorite_border_24px)
        }
    }

    private fun removeFromFavourite(){
        try {
            database.use {
                delete(Favourite.TABLE_FAVOURITE, "(MATCH_ID = {id})", "id" to matchId)
            }
            Snackbar.make(window.decorView.rootView, ctx.getString(R.string.favourite_removed), Snackbar.LENGTH_LONG).show()
        } catch (e: SQLiteConstraintException) {
            Snackbar.make(window.decorView.rootView, e.localizedMessage, Snackbar.LENGTH_LONG).show()
        }
    }

    private fun addFavourite(){
        try {
            database.use {
                insert(Favourite.TABLE_FAVOURITE,
                        Favourite.MATCH_ID to match.matchId,
                        Favourite.HOME_TEAM_NAME to match.homeTeamName,
                        Favourite.HOME_TEAM_SCORE to match.homeTeamScore,
                        Favourite.HOME_TEAM_BADGE to urlHomeTeamBadge,
                        Favourite.AWAY_TEAM_NAME to match.awayTeamName,
                        Favourite.AWAY_TEAM_SCORE to match.awayTeamScore,
                        Favourite.AWAY_TEAM_BADGE to urlAwayTeamBadge)
            }
            Snackbar.make(window.decorView.rootView, ctx.getString(R.string.favourite_added), Snackbar.LENGTH_LONG).show()
        } catch (e: SQLiteConstraintException) {
            Snackbar.make(window.decorView.rootView, e.localizedMessage, Snackbar.LENGTH_LONG).show()
        }
    }

    override fun showMatchDetail(match: MatchSchedule){
        this.match = match
        showHeader(match)
        showGoals(match)
        showCards(match)
        showLineups(match)
        showSubs(match)
    }

    override fun showHeader(match: MatchSchedule){
        // Team Header
        tv_home_team_name.text = match.homeTeamName
        tv_away_team_name.text = match.awayTeamName

        // Match Score
        val matchScore = String.format(ctx.getString(R.string.blank_scores2), match.homeTeamScore, match.awayTeamScore)
        if (!matchScore.contains("null")) {
            tv_match_scores.text = matchScore
        } else {
            tv_match_scores.text = ctx.getString(R.string.match_scores2)
        }
    }

    override fun showGoals(match: MatchSchedule){
        // Goals Details
        tv_home_scores.text = match.homeTeamGoalDetail?.replace(";", "\n")
        tv_away_scores.text = match.awayTeamGoalDetail?.replace(";", "\n")
    }

    override fun showCards(match: MatchSchedule){
        // Yellow Cards & Red Cards
        tv_home_yellow.text = match.homeTeamYellowCards?.replace(";", "\n")
        tv_away_yellow.text = match.awayTeamYellowCards?.replace(";", "\n")
        tv_home_red.text = match.homeTeamRedCards?.replace(";", "\n")
        tv_away_red.text = match.awayTeamRedCards?.replace(";", "\n")
    }

    override fun showSubs(match: MatchSchedule){
        // Home Subs
        val homeSubs = String.format(ctx.getString(R.string.blank), match.homeTeamLineupSubs?.replace(";", "\n"))
        if (!homeSubs.contains("null")){
            tv_home_subs.text = homeSubs
        } else {
            tv_home_subs.text = ctx.getString(R.string.empty)
        }

        // Away Subs
        val awaySubs = String.format(ctx.getString(R.string.blank), match.awayTeamLineupSubs?.replace(";", "\n"))
        if (!awaySubs.contains("null")){
            tv_away_subs.text = awaySubs
        } else {
            tv_away_subs.text = ctx.getString(R.string.empty)
        }
    }

    override fun showLineups(match: MatchSchedule){
        // Home Lineups
        val homeLineups: String = match.homeTeamLineupGK + match.homeTeamLineupDef + match.homeTeamLineupMid + match.homeTeamLineupFw
        if (!homeLineups.contains("null")){
            tv_home_lineup.text = String.format(ctx.getString(R.string.blank), homeLineups.replace(";", "\n"))
        } else {
            tv_home_lineup.text = ctx.getString(R.string.empty)
        }

        // Away Lineups
        val awayLineups: String = match.awayTeamLineupGK + match.awayTeamLineupDef + match.awayTeamLineupMid + match.awayTeamLineupFw
        if (!awayLineups.contains("null")){
            tv_away_lineup.text = String.format(ctx.getString(R.string.blank), awayLineups.replace(";", "\n"))
        } else {
            tv_away_lineup.text = ctx.getString(R.string.empty)
        }
    }

    override fun loadHomeBadge(urlHomeTeamBadge: String){
        this.urlHomeTeamBadge = urlHomeTeamBadge
        Picasso.get().load(urlHomeTeamBadge).into(img_home_team)
    }

    override fun loadAwayBadge(urlAwayTeamBadge: String){
        this.urlAwayTeamBadge = urlAwayTeamBadge
        Picasso.get().load(urlAwayTeamBadge).into(img_away_team)
    }

    override fun showLoading() {
        progress_bar_detail.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progress_bar_detail.visibility = View.GONE
    }
}
