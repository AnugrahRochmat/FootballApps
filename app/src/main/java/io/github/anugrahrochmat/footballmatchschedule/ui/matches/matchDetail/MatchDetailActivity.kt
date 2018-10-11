package io.github.anugrahrochmat.footballmatchschedule.ui.matches.matchDetail

import android.content.Context
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.squareup.picasso.Picasso
import io.github.anugrahrochmat.footballmatchschedule.R
import io.github.anugrahrochmat.footballmatchschedule.R.drawable.ic_white_favorite_24dp
import io.github.anugrahrochmat.footballmatchschedule.R.drawable.ic_white_favorite_border_24dp
import io.github.anugrahrochmat.footballmatchschedule.R.id.add_favourite
import io.github.anugrahrochmat.footballmatchschedule.R.layout.activity_match_detail
import io.github.anugrahrochmat.footballmatchschedule.R.menu.detail_menu
import io.github.anugrahrochmat.footballmatchschedule.data.models.Favourite
import io.github.anugrahrochmat.footballmatchschedule.data.models.MatchSchedule
import kotlinx.android.synthetic.main.activity_match_detail.*
import org.jetbrains.anko.ctx
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

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

    companion object {
        const val EXTRA_MATCH_ID = "match_id"
        const val EXTRA_HOME_TEAM_NAME = "homeTeamName"
        const val EXTRA_AWAY_TEAM_NAME = "awayTeamName"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_match_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = ctx.getString(R.string.match_detail)

        matchId = intent.getStringExtra(EXTRA_MATCH_ID).toString()
        homeTeamName = intent.getStringExtra(EXTRA_HOME_TEAM_NAME).toString()
        awayTeamName = intent.getStringExtra(EXTRA_AWAY_TEAM_NAME).toString()

        presenter = MatchDetailPresenter(this)
        presenter.getMatchDetail(matchId)
        presenter.getTeamBadges(homeTeamName, awayTeamName)

        presenter.onViewAttached()

        favouriteState()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onViewDestroyed()
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
        presenter.getFavorites(matchId)
    }

    private fun setFavourite() {
        if (isFavorite) {
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_white_favorite_24dp)
        }
        else {
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_white_favorite_border_24dp)
        }
    }

    private fun removeFromFavourite(){
        presenter.deleteFavorites(matchId)
    }

    private fun addFavourite(){
        presenter.insertFavorites(match, urlHomeTeamBadge, urlAwayTeamBadge)
    }

    override fun showMatchDetail(match: MatchSchedule){
        this.match = match
        showHeader(match)
        showGoals(match)
        showCards(match)
        showLineups(match)
        showSubs(match)
        showDateTime(match)
    }

    override fun showDateTime(match: MatchSchedule) {
        val dateParsed = LocalDate.parse(match.dateEvent, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        val dateFormatted = dateParsed.format(DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy"))
        val timeParsed = LocalTime.parse(match.strTime, DateTimeFormatter.ISO_OFFSET_TIME)
        val timeFormatted = timeParsed.plusHours(7).toString()
        tv_date_time_detail.text = "${dateFormatted}  (${timeFormatted} WIB)"
    }

    override fun showHeader(match: MatchSchedule){
        // Team Header
        tv_home_team_name_detail.text = match.homeTeamName
        tv_away_team_name_detail.text = match.awayTeamName

        // Match Score
        val matchScore = String.format(ctx.getString(R.string.blank_scores2), match.homeTeamScore, match.awayTeamScore)
        if (!matchScore.contains("null")) {
            tv_match_scores_detail.text = matchScore
        } else {
            tv_match_scores_detail.text = ctx.getString(R.string.match_scores2)
        }
    }

    override fun showGoals(match: MatchSchedule){
        // Goals Details
        tv_home_scores_detail.text = match.homeTeamGoalDetail?.replace(";", "\n")
        tv_away_scores_detail.text = match.awayTeamGoalDetail?.replace(";", "\n")
    }

    override fun showCards(match: MatchSchedule){
        // Yellow Cards & Red Cards
        tv_home_yellow_detail.text = match.homeTeamYellowCards?.replace(";", "\n")
        tv_away_yellow_detail.text = match.awayTeamYellowCards?.replace(";", "\n")
        tv_home_red_detail.text = match.homeTeamRedCards?.replace(";", "\n")
        tv_away_red_detail.text = match.awayTeamRedCards?.replace(";", "\n")
    }

    override fun showSubs(match: MatchSchedule){
        // Home Subs
        val homeSubs = String.format(ctx.getString(R.string.blank), match.homeTeamLineupSubs?.replace(";", "\n"))
        if (!homeSubs.contains("null")){
            tv_home_subs_detail.text = homeSubs
        } else {
            tv_home_subs_detail.text = ctx.getString(R.string.empty)
        }

        // Away Subs
        val awaySubs = String.format(ctx.getString(R.string.blank), match.awayTeamLineupSubs?.replace(";", "\n"))
        if (!awaySubs.contains("null")){
            tv_away_subs_detail.text = awaySubs
        } else {
            tv_away_subs_detail.text = ctx.getString(R.string.empty)
        }
    }

    override fun showLineups(match: MatchSchedule){
        // Home Lineups
        val homeLineups: String = match.homeTeamLineupGK + match.homeTeamLineupDef + match.homeTeamLineupMid + match.homeTeamLineupFw
        if (!homeLineups.contains("null")){
            tv_home_lineup_detail.text = String.format(ctx.getString(R.string.blank), homeLineups.replace(";", "\n"))
        } else {
            tv_home_lineup_detail.text = ctx.getString(R.string.empty)
        }

        // Away Lineups
        val awayLineups: String = match.awayTeamLineupGK + match.awayTeamLineupDef + match.awayTeamLineupMid + match.awayTeamLineupFw
        if (!awayLineups.contains("null")){
            tv_away_lineup_detail.text = String.format(ctx.getString(R.string.blank), awayLineups.replace(";", "\n"))
        } else {
            tv_away_lineup_detail.text = ctx.getString(R.string.empty)
        }
    }

    override fun loadHomeBadge(urlHomeTeamBadge: String){
        this.urlHomeTeamBadge = urlHomeTeamBadge
        Picasso.get().load(urlHomeTeamBadge).into(img_home_team_detail)
    }

    override fun loadAwayBadge(urlAwayTeamBadge: String){
        this.urlAwayTeamBadge = urlAwayTeamBadge
        Picasso.get().load(urlAwayTeamBadge).into(img_away_team_detail)
    }

    override fun getContext(): Context {
        return this
    }

    override fun showLoading() {
        progress_bar_detail.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progress_bar_detail.visibility = View.GONE
    }

    override fun showFavorites(favorites: List<Favourite>) {
        if (!favorites.isEmpty()) isFavorite = true
    }

    override fun showFavoriteInserted(rowId: Long) {
        if (rowId > 0) {
            Snackbar.make(window.decorView.rootView, getString(R.string.favourite_added), Snackbar.LENGTH_LONG).show()
        } else {
            Snackbar.make(window.decorView.rootView, getString(R.string.error_add_favourite), Snackbar.LENGTH_LONG).show()
        }
    }

    override fun showFavouriteDeleted(rowAffected: Int) {
        if (rowAffected > 0) {
            Snackbar.make(window.decorView.rootView, getString(R.string.favourite_removed), Snackbar.LENGTH_LONG).show()
        } else {
            Snackbar.make(window.decorView.rootView, getString(R.string.error_remove_favourite), Snackbar.LENGTH_LONG).show()
        }
    }
}
