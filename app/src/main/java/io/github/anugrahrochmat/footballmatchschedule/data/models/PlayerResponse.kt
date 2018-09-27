package io.github.anugrahrochmat.footballmatchschedule.data.models

import com.google.gson.annotations.SerializedName

data class PlayerResponse (
        @SerializedName("player")
        var players: List<Player>? = null
)