package io.github.anugrahrochmat.footballmatchschedule.ui.match_detail

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import com.squareup.picasso.Picasso
import io.github.anugrahrochmat.footballmatchschedule.R
import io.github.anugrahrochmat.footballmatchschedule.R.layout.activity_match_detail
import io.github.anugrahrochmat.footballmatchschedule.data.models.MatchSchedule
import kotlinx.android.synthetic.main.activity_match_detail.*
import org.jetbrains.anko.ctx

class MatchDetailActivity : AppCompatActivity(), MatchDetailView {
    private lateinit var presenter: MatchDetailPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_match_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = ctx.getString(R.string.match_detail)

        val match: MatchSchedule = intent.getSerializableExtra("match") as MatchSchedule

        presenter = MatchDetailPresenter(this)
        presenter.getTeamBadges(match.homeTeamName, match.awayTeamName)
        presenter.loadTeamDetail(match)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return if (item?.itemId == android.R.id.home) {
            finish()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
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

    override fun loadHomeBadge(urlHomeTeamBadge: String?){
        Picasso.get().load(urlHomeTeamBadge).into(img_home_team)
    }

    override fun loadAwayBadge(urlAwayTeamBadge: String?){
        Picasso.get().load(urlAwayTeamBadge).into(img_away_team)
    }

    override fun showLoading() {
        progress_bar_detail.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progress_bar_detail.visibility = View.GONE
    }
}
