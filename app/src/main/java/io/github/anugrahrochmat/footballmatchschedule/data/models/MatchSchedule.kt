package io.github.anugrahrochmat.footballmatchschedule.data.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MatchSchedule(
        @SerializedName("idEvent")
        var matchId: String? = null,

        @SerializedName("strHomeTeam")
        var homeTeamName: String? = null,

        @SerializedName("strAwayTeam")
        var awayTeamName: String? = null,

        @SerializedName("intHomeScore")
        var homeTeamScore: Int? = null,

        @SerializedName("intAwayScore")
        var awayTeamScore: Int? = null,

        @SerializedName("dateEvent")
        var dateEvent: String? = null,

        @SerializedName("strDate")
        var strDate: String? = null,

        @SerializedName("strTime")
        var strTime: String? = null,

        // Home team Details
        @SerializedName("strHomeGoalDetails")
        var homeTeamGoalDetail: String? = null,

        @SerializedName("strHomeRedCards")
        var homeTeamRedCards: String? = null,

        @SerializedName("strHomeYellowCards")
        var homeTeamYellowCards: String? = null,

        @SerializedName("strHomeLineupGoalkeeper")
        var homeTeamLineupGK: String? = null,

        @SerializedName("strHomeLineupDefense")
        var homeTeamLineupDef: String? = null,

        @SerializedName("strHomeLineupMidfield")
        var homeTeamLineupMid: String? = null,

        @SerializedName("strHomeLineupForward")
        var homeTeamLineupFw: String? = null,

        @SerializedName("strHomeLineupSubstitutes")
        var homeTeamLineupSubs: String? = null,

        // Away team Details
        @SerializedName("strAwayGoalDetails")
        var awayTeamGoalDetail: String? = null,

        @SerializedName("strAwayRedCards")
        var awayTeamRedCards: String? = null,

        @SerializedName("strAwayYellowCards")
        var awayTeamYellowCards: String? = null,

        @SerializedName("strAwayLineupGoalkeeper")
        var awayTeamLineupGK: String? = null,

        @SerializedName("strAwayLineupDefense")
        var awayTeamLineupDef: String? = null,

        @SerializedName("strAwayLineupMidfield")
        var awayTeamLineupMid: String? = null,

        @SerializedName("strAwayLineupForward")
        var awayTeamLineupFw: String? = null,

        @SerializedName("strAwayLineupSubstitutes")
        var awayTeamLineupSubs: String? = null
) : Parcelable