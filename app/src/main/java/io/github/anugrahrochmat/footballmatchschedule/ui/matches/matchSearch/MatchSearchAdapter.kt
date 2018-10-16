package io.github.anugrahrochmat.footballmatchschedule.ui.matches.matchSearch

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import io.github.anugrahrochmat.footballmatchschedule.R
import io.github.anugrahrochmat.footballmatchschedule.data.api.ApiClient
import io.github.anugrahrochmat.footballmatchschedule.data.api.ApiInterface
import io.github.anugrahrochmat.footballmatchschedule.data.models.MatchSchedule
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.match_schedule_item_list.*
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

/**
 *  Created by Anugrah Rochmat on 13/10/18
 */
class MatchSearchAdapter(private val searchMatches: List<MatchSchedule>, private val listener: (MatchSchedule) -> Unit)
    : RecyclerView.Adapter<MatchSearchViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchSearchViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.match_schedule_item_list, parent, false)
        return MatchSearchViewHolder(view)
    }

    override fun onBindViewHolder(holder: MatchSearchViewHolder, position: Int) {
        holder.bindItem(searchMatches[position], listener)
    }

    override fun getItemCount(): Int = searchMatches.size

}

class MatchSearchViewHolder(override val containerView: View): RecyclerView.ViewHolder(containerView), LayoutContainer {
    fun bindItem(searchMatch: MatchSchedule, listener: (MatchSchedule) -> Unit){
        tv_home_team_name.text = searchMatch.homeTeamName
        tv_away_team_name.text = searchMatch.awayTeamName
        tv_match_date.text = searchMatch.dateEvent
        tv_match_time.text = searchMatch.strTime
        tv_home_scores.text = parseScore(searchMatch.homeTeamScore.toString())
        tv_away_scores.text = parseScore(searchMatch.awayTeamScore.toString())


        val dateFormatted = LocalDate.parse(searchMatch.dateEvent, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        tv_match_date.text = dateFormatted.format(DateTimeFormatter.ofPattern("EEE, dd MMM yyyy"))




        if (!searchMatch.strTime.isNullOrEmpty()) {
            checkSearchTime(searchMatch.strTime.toString())
        } else {
            tv_match_time.text = "Null"
        }


        if (!searchMatch.homeTeamName.isNullOrEmpty()) {
            getHomeTeamBadge(searchMatch.homeTeamName)
        }

        if (!searchMatch.awayTeamName.isNullOrEmpty()){
            getAwayTeamBadge(searchMatch.awayTeamName)
        }


        containerView.setOnClickListener { listener(searchMatch) }
    }

    private fun checkSearchTime(strTime: String){
        if(strTime.length == 14) {
            val timeFormatted = LocalTime.parse(strTime, DateTimeFormatter.ISO_OFFSET_TIME)
            tv_match_time.text = timeFormatted.plusHours(7).toString()
        } else if(strTime.length == 8) {
            val timeFormatted = LocalTime.parse(strTime, DateTimeFormatter.ISO_LOCAL_TIME)
            tv_match_time.text = timeFormatted.plusHours(7).toString()
        } else {
            tv_match_time.text = strTime
        }
    }

    private fun parseScore(teamScore: String): String{
        if (!teamScore.contains("null")){
            return teamScore
        } else {
            return "-"
        }
    }

    @SuppressLint("CheckResult")
    private fun getHomeTeamBadge(teamName: String?) {
        val apiServices = ApiClient.client.create(ApiInterface::class.java)
        apiServices.getTeams(teamName!!)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            val result = it.teams!![0].teamBadge
                            if (!result.isNullOrEmpty()) {
                                Picasso.get().load(result.toString()).into(img_home_team)
                            } else {
                                Picasso.get().load(R.drawable.img_placeholder).into(img_home_team)
                            }
                        },
                        {
                            error -> Log.e("Error", error.message)
                        }
                )
    }

    @SuppressLint("CheckResult")
    private fun getAwayTeamBadge(teamName: String?) {
        val apiServices = ApiClient.client.create(ApiInterface::class.java)
        apiServices.getTeams(teamName!!)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            val result = it.teams!![0].teamBadge
                            if (!result.isNullOrEmpty()) {
                                Picasso.get().load(result.toString()).into(img_away_team)
                            } else {
                                Picasso.get().load(R.drawable.img_placeholder).into(img_away_team)
                            }
                        },
                        {
                            error -> Log.e("Error", error.message)
                        }
                )
    }

}