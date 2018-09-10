package io.github.anugrahrochmat.footballmatchschedule.data.model

import com.google.gson.annotations.SerializedName

data class TeamResponse (
        @SerializedName("teams")
        var teams: List<Team>? = null
)