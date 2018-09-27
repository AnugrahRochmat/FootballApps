package io.github.anugrahrochmat.footballmatchschedule.data.models

import com.google.gson.annotations.SerializedName

data class Leagues(
        @SerializedName("idLeague")
        var leagueId: String? = null,

        @SerializedName("strLeague")
        var leagueName: String? = null
)