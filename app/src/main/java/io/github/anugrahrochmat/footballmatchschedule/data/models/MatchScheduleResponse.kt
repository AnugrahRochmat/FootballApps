package io.github.anugrahrochmat.footballmatchschedule.data.models

import com.google.gson.annotations.SerializedName

data class MatchScheduleResponse(
        @SerializedName("events")
        var matchSchedules: List<MatchSchedule>? = null
)