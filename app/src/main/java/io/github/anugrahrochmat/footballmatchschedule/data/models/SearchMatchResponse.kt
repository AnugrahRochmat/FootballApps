package io.github.anugrahrochmat.footballmatchschedule.data.models

import com.google.gson.annotations.SerializedName

/**
 *  Created by Anugrah Rochmat on 14/10/18
 */
class SearchMatchResponse {
    @SerializedName("event")
    var matchSchedules: List<MatchSchedule>? = null
}