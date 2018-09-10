package io.github.anugrahrochmat.footballmatchschedule.ui.MatchDetail

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import com.squareup.picasso.Picasso
import io.github.anugrahrochmat.footballmatchschedule.R
import io.github.anugrahrochmat.footballmatchschedule.data.api.ApiClient
import io.github.anugrahrochmat.footballmatchschedule.data.api.ApiInterface
import io.github.anugrahrochmat.footballmatchschedule.data.model.MatchSchedule
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_match_detail.*
import org.jetbrains.anko.ctx

class MatchDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = ctx.getString(R.string.match_detail)

        val match: MatchSchedule = intent.getSerializableExtra("match") as MatchSchedule

        // Team Header
        tv_home_team_name.text = match.homeTeamName
        tv_away_team_name.text = match.awayTeamName
        getHomeTeamBadge(match.homeTeamName)
        getAwayTeamBadge(match.awayTeamName)

        // Match Score
        val matchScore = String.format(ctx.getString(R.string.blank_scores2), match.homeTeamScore, match.awayTeamScore)
        if (!matchScore.contains("null")) {
            tv_match_scores.text = matchScore
        } else {
            tv_match_scores.text = ctx.getString(R.string.match_scores2)
        }

        // Goals Details
        tv_home_scores.text = match.homeTeamGoalDetail?.replace(";", "\n")
        tv_away_scores.text = match.awayTeamGoalDetail?.replace(";", "\n")

        // Yellow Cards & Red Cards
        tv_home_yellow.text = match.homeTeamYellowCards?.replace(";", "\n")
        tv_away_yellow.text = match.awayTeamYellowCards?.replace(";", "\n")
        tv_home_red.text = match.homeTeamRedCards?.replace(";", "\n")
        tv_away_red.text = match.awayTeamRedCards?.replace(";", "\n")

        // Home Lineups
        val homeLineups: String = match.homeTeamLineupGK + match.homeTeamLineupDef + match.homeTeamLineupMid + match.homeTeamLineupFw
        if (!homeLineups.contains("null")){
            tv_home_lineup.text = String.format(ctx.getString(R.string.blank), homeLineups.replace(";", "\n"))
        } else {
            tv_home_lineup.text = ctx.getString(R.string.empty)
        }

        // Home Subs
        val homeSubs = String.format(ctx.getString(R.string.blank), match.homeTeamLineupSubs?.replace(";", "\n"))
        if (!homeSubs.contains("null")){
            tv_home_subs.text = homeSubs
        } else {
            tv_home_subs.text = ctx.getString(R.string.empty)
        }

        // Away Lineups
        val awayLineups: String = match.awayTeamLineupGK + match.awayTeamLineupDef + match.awayTeamLineupMid + match.awayTeamLineupFw
        if (!awayLineups.contains("null")){
            tv_away_lineup.text = String.format(ctx.getString(R.string.blank), awayLineups.replace(";", "\n"))
        } else {
            tv_away_lineup.text = ctx.getString(R.string.empty)
        }

        // Away Subs
        val awaySubs = String.format(ctx.getString(R.string.blank), match.awayTeamLineupSubs?.replace(";", "\n"))
        if (!awaySubs.contains("null")){
            tv_away_subs.text = awaySubs
        } else {
            tv_away_subs.text = ctx.getString(R.string.empty)
        }

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return if (item?.itemId == android.R.id.home) {
            finish()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    private fun getHomeTeamBadge(teamName: String?) {
        val apiServices = ApiClient.client.create(ApiInterface::class.java)
        apiServices.getTeams(teamName!!)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            result -> Picasso.get().load(result.teams!![0].teamBadge.toString()).into(img_home_team)
                        },
                        {
                            error -> Log.e("Error", error.message)
                        }
                )
    }

    private fun getAwayTeamBadge(teamName: String?) {
        val apiServices = ApiClient.client.create(ApiInterface::class.java)
        apiServices.getTeams(teamName!!)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            result -> Picasso.get().load(result.teams!![0].teamBadge.toString()).into(img_away_team)
                        },
                        {
                            error -> Log.e("Error", error.message)
                        }
                )
    }
}
