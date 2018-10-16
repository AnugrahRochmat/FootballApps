package io.github.anugrahrochmat.footballmatchschedule.ui.favourites.matchFavourites

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import io.github.anugrahrochmat.footballmatchschedule.R
import io.github.anugrahrochmat.footballmatchschedule.data.api.ApiClient
import io.github.anugrahrochmat.footballmatchschedule.data.api.ApiInterface
import io.github.anugrahrochmat.footballmatchschedule.data.models.Favourite
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.match_schedule_item_list.*
import java.text.SimpleDateFormat
import java.util.*


class MatchFavouritesAdapter(private val favMatches: List<Favourite>, private val listener: (Favourite) -> Unit)
    : RecyclerView.Adapter<MatchFavouritesViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchFavouritesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.match_schedule_item_list, parent, false)
        return MatchFavouritesViewHolder(view)
    }

    override fun onBindViewHolder(holder: MatchFavouritesViewHolder, position: Int) {
        holder.bindItem(favMatches[position], listener)
    }

    override fun getItemCount(): Int = favMatches.size

}

class MatchFavouritesViewHolder(override val containerView: View): RecyclerView.ViewHolder(containerView), LayoutContainer {
    private val apiServices = ApiClient.client.create(ApiInterface::class.java)

    fun bindItem(favMatches: Favourite, listener: (Favourite) -> Unit){
        tv_home_team_name.text = favMatches.homeTeamName
        tv_away_team_name.text = favMatches.awayTeamName
        tv_home_scores.text = parseScore(favMatches.homeTeamScore.toString())
        tv_away_scores.text = parseScore(favMatches.awayTeamScore.toString())


        val dateAndTimeFormatted: Date? = toGMTFormat(favMatches.dateEvent, favMatches.strTime)
        val dateFormatted = SimpleDateFormat("EEE, dd MMMM yyyy")
        val timeFormatted = SimpleDateFormat("HH:mm:ss")

        tv_match_date.text = dateFormatted.format(dateAndTimeFormatted)
        tv_match_time.text = timeFormatted.format(dateAndTimeFormatted)

        getHomeTeamBadge(favMatches.homeTeamName)
        getAwayTeamBadge(favMatches.awayTeamName)

        containerView.setOnClickListener { listener(favMatches) }
    }

    private fun toGMTFormat (date: String, time: String): Date? {
        val formatter = SimpleDateFormat ("yyyy-MM-dd HH:mm:ss")
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

