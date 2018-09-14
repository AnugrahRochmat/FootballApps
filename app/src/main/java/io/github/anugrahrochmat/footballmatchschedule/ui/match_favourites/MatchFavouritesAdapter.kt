package io.github.anugrahrochmat.footballmatchschedule.ui.match_favourites

import android.graphics.Typeface
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import io.github.anugrahrochmat.footballmatchschedule.R
import io.github.anugrahrochmat.footballmatchschedule.data.api.ApiClient
import io.github.anugrahrochmat.footballmatchschedule.data.api.ApiInterface
import io.github.anugrahrochmat.footballmatchschedule.data.models.Favourite
import io.github.anugrahrochmat.footballmatchschedule.ui.match_schedule.MatchScheduleUI
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.extensions.LayoutContainer
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView

class MatchFavouritesAdapter(private val favMatches: List<Favourite>, private val listener: (Favourite) -> Unit)
    : RecyclerView.Adapter<MatchFavouritesViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchFavouritesViewHolder {
        return MatchFavouritesViewHolder(MatchScheduleUI().createView(AnkoContext.create(parent.context, parent)))
    }

    override fun onBindViewHolder(holder: MatchFavouritesViewHolder, position: Int) {
        holder.bindItem(favMatches[position], listener)
    }

    override fun getItemCount(): Int = favMatches.size

}

class MatchFavouritesViewHolder(override val containerView: View): RecyclerView.ViewHolder(containerView), LayoutContainer  {
    private val tvHomeTeam: TextView = itemView.findViewById(MatchScheduleUI.tvHomeTeamID)
    private val tvAwayTeam: TextView = itemView.findViewById(MatchScheduleUI.tvAwayTeamID)
    private val tvMatchScores: TextView = itemView.findViewById(MatchScheduleUI.tvMatchScoresID)
    private val imgHomeTeam: ImageView = itemView.findViewById(MatchScheduleUI.imgHomeTeamID)
    private val imgAwayTeam: ImageView = itemView.findViewById(MatchScheduleUI.imgAwayTeamID)

    fun bindItem(favMatches: Favourite, listener: (Favourite) -> Unit){
        tvHomeTeam.text = favMatches.homeTeamName
        tvAwayTeam.text = favMatches.awayTeamName

        val matchScore = String.format(containerView.context.getString(R.string.blank_scores), favMatches.homeTeamScore, favMatches.awayTeamScore)
        if (!matchScore.contains("null")) {
            tvMatchScores.text = matchScore
        } else {
            tvMatchScores.text = containerView.context.getString(R.string.match_scores)
        }

        getHomeTeamBadge(favMatches.homeTeamName)
        getAwayTeamBadge(favMatches.awayTeamName)

        containerView.setOnClickListener { listener(favMatches) }
    }

    private fun getHomeTeamBadge(teamName: String?) {
        val apiServices = ApiClient.client.create(ApiInterface::class.java)
        apiServices.getTeams(teamName!!)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            result -> Picasso.get().load(result.teams!![0].teamBadge.toString()).into(imgHomeTeam)
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
                            result -> Picasso.get().load(result.teams!![0].teamBadge.toString()).into(imgAwayTeam)
                        },
                        {
                            error -> Log.e("Error", error.message)
                        }
                )
    }
}

class MatchFavouritesUI : AnkoComponent<ViewGroup> {
    companion object {
        const val tvHomeTeamID = 1
        const val tvAwayTeamID = 2
        const val imgHomeTeamID = 3
        const val imgAwayTeamID = 4
        const val tvMatchScoresID = 5
    }

    override fun createView(ui: AnkoContext<ViewGroup>) = with(ui) {
        cardView {
            lparams(matchParent, wrapContent)

            linearLayout {
                lparams(matchParent, wrapContent)
                padding = dip(5)
                gravity = Gravity.CENTER

                // Home Team
                linearLayout {
                    lparams(dip(170), wrapContent)
                    gravity = Gravity.RIGHT

                    textView {
                        id = tvHomeTeamID
                        text = ctx.getString(R.string.home_team)
                        textSize = 14f
                        minLines = 2
                        gravity = Gravity.CENTER
                        ellipsize = TextUtils.TruncateAt.END
                    }.lparams(wrapContent, wrapContent) {
                        gravity = Gravity.CENTER_VERTICAL
                    }

                    imageView {
                        id = imgHomeTeamID
//                        setImageResource(R.drawable.img_acm)
                    }.lparams(dip(40), dip(40)){
                        leftMargin = dip(5)
                        rightMargin = dip(5)
                    }
                }

                // Match Scores
                textView {
                    id = tvMatchScoresID
                    text = ctx.getString(R.string.match_scores)
                    textSize = 16f
                    typeface = Typeface.DEFAULT_BOLD
                }.lparams(dip(40), wrapContent) {
                    gravity = Gravity.CENTER
                }

                // Away Team
                linearLayout {
                    lparams(dip(170), wrapContent)
                    gravity = Gravity.LEFT

                    imageView {
                        id = imgAwayTeamID
//                        setImageResource(R.drawable.img_acm)
                    }.lparams(dip(40), dip(40)){
                        leftMargin = dip(5)
                        rightMargin = dip(5)
                    }

                    textView {
                        id = tvAwayTeamID
                        text = ctx.getString(R.string.away_team)
                        textSize = 14f
                        minLines = 2
                        gravity = Gravity.CENTER
                        ellipsize = TextUtils.TruncateAt.END
                    }.lparams(wrapContent, wrapContent) {
                        gravity = Gravity.CENTER
                    }
                }
            }
            view {
                backgroundColor = R.color.darker_gray
            }.lparams(matchParent, dip(1))
        }
    }
}
