package io.github.anugrahrochmat.footballmatchschedule.data.api

import io.github.anugrahrochmat.footballmatchschedule.data.models.MatchScheduleResponse
import io.github.anugrahrochmat.footballmatchschedule.data.models.TeamResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("eventspastleague.php")
    fun getPreviousSchedules(@Query("id") id: String): Observable<MatchScheduleResponse>

    @GET("eventsnextleague.php")
    fun getNextSchedules(@Query("id") id: String): Observable<MatchScheduleResponse>

    @GET("searchteams.php")
    fun getTeams(@Query("t") t: String): Observable<TeamResponse>

}