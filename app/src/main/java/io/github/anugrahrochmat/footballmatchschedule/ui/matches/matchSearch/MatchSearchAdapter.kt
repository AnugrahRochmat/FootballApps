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
import java.text.SimpleDateFormat
import java.util.*

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
    private val apiServices = ApiClient.client.create(ApiInterface::class.java)


    fun bindItem(searchMatch: MatchSchedule, listener: (MatchSchedule) -> Unit){
        tv_home_team_name.text = searchMatch.homeTeamName
        tv_away_team_name.text = searchMatch.awayTeamName
        tv_home_scores.text = parseScore(searchMatch.homeTeamScore.toString())
        tv_away_scores.text = parseScore(searchMatch.awayTeamScore.toString())

        if (!searchMatch.strTime.isNullOrEmpty()) {
            val dateAndTimeFormatted: Date? = toGMTFormat(searchMatch.dateEvent!!, searchMatch.strTime!!)
            val dateFormatted = SimpleDateFormat("EEE, dd MMMM yyyy")
            val timeFormatted = SimpleDateFormat("HH:mm:ss")

            tv_match_date.text = dateFormatted.format(dateAndTimeFormatted)
            tv_match_time.text = timeFormatted.format(dateAndTimeFormatted)
        } else {
            tv_match_date.text = containerView.resources.getString(R.string.nullvalue)
            tv_match_time.text = containerView.resources.getString(R.string.nullvalue)
        }

        if (!searchMatch.homeTeamName.isNullOrEmpty()) {
            getHomeTeamBadge(searchMatch.homeTeamName)
        }

        if (!searchMatch.awayTeamName.isNullOrEmpty()){
            getAwayTeamBadge(searchMatch.awayTeamName)
        }


        containerView.setOnClickListener { listener(searchMatch) }
    }

    private fun toGMTFormat (date: String, time: String): Date? {
        var formatter: SimpleDateFormat
        if(time.length >= 8) {
            formatter = SimpleDateFormat ("yyyy-MM-dd HH:mm:ss")
        } else {
            formatter = SimpleDateFormat ("yyyy-MM-dd HH:mm")
        }
        formatter.timeZone = TimeZone.getTimeZone ("UTC")
        val dateTime = "$date $time"
        return formatter.parse(dateTime)
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