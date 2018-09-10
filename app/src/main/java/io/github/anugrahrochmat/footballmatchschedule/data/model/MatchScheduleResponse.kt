package io.github.anugrahrochmat.footballmatchschedule.data.model

import com.google.gson.annotations.SerializedName

data class MatchScheduleResponse(
        @SerializedName("events")
        var matchSchedules: List<MatchSchedule>? = null
)