package io.github.anugrahrochmat.footballmatchschedule.ui.matches.matchSchedule

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

class MatchScheduleAdapter(private val matches: List<MatchSchedule>, private val listener: (MatchSchedule) -> Unit)
    : RecyclerView.Adapter<MatchScheduleViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchScheduleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.match_schedule_item_list, parent, false)
        return MatchScheduleViewHolder(view)
    }

    override fun onBindViewHolder(holder: MatchScheduleViewHolder, position: Int) {
        holder.bindItem(matches[position], listener)
    }

    override fun getItemCount(): Int = matches.size

}

class MatchScheduleViewHolder(override val containerView: View): RecyclerView.ViewHolder(containerView), LayoutContainer {
    private val apiServices = ApiClient.client.create(ApiInterface::class.java)

    fun bindItem(matches: MatchSchedule, listener: (MatchSchedule) -> Unit){
        tv_home_team_name.text = matches.homeTeamName
        tv_away_team_name.text = matches.awayTeamName
        tv_home_scores.text = parseScore(matches.homeTeamScore.toString())
        tv_away_scores.text = parseScore(matches.awayTeamScore.toString())

        val dateAndTimeFormatted: Date? = toGMTFormat(matches.strDate!!, matches.strTime!!)
        val dateFormatted = SimpleDateFormat("EEE, dd MMMM yyyy")
        val timeFormatted = SimpleDateFormat("HH:mm:ss")

        tv_match_date.text = dateFormatted.format(dateAndTimeFormatted)
        tv_match_time.text = timeFormatted.format(dateAndTimeFormatted)

        getHomeTeamBadge(matches.homeTeamName)
        getAwayTeamBadge(matches.awayTeamName)

        containerView.setOnClickListener { listener(matches) }
    }

    private fun toGMTFormat (date: String, time: String): Date? {
        val formatter = SimpleDateFormat ("dd/MM/yy HH:mm:ss")
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

    private fun getHomeTeamBadge(teamName: String?) {
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