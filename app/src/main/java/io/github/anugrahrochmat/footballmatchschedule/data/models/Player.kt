package io.github.anugrahrochmat.footballmatchschedule.data.models

import com.google.gson.annotations.SerializedName

data class Player (

    @SerializedName("idPlayer")
    var playerId: String? = null,

    @SerializedName("strNationality")
    var playerNationality: String? = null,

    @SerializedName("strPlayer")
    var playerName: String? = null,

    @SerializedName("strTeam")
    var playerTeam: String? = null,

    @SerializedName("dateBorn")
    var playerBirthDate: String? = null,

    @SerializedName("strDescriptionEN")
    var playerDesc: String? = null,

    @SerializedName("strPosition")
    var playerPosition: String? = null,

    @SerializedName("strThumb")
    var playerThumbImage: String? = null
)