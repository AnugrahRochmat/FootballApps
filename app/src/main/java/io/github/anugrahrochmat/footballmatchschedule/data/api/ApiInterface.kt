package io.github.anugrahrochmat.footballmatchschedule.data.api

import io.github.anugrahrochmat.footballmatchschedule.data.models.MatchScheduleResponse
import io.github.anugrahrochmat.footballmatchschedule.data.models.PlayerResponse
import io.github.anugrahrochmat.footballmatchschedule.data.models.SearchMatchResponse
import io.github.anugrahrochmat.footballmatchschedule.data.models.TeamResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("eventspastleague.php")
    fun getPreviousSchedules(@Query("id") id: String): Observable<MatchScheduleResponse>

    @GET("eventsnextleague.php")
    fun getNextSchedules(@Query("id") id: String): Observable<MatchScheduleResponse>

    @GET("lookupevent.php")
    fun getMatchDetail(@Query("id") id: String): Observable<MatchScheduleResponse>

    @GET("searchevents.php?")
    fun getSearchMatch(@Query("e") id: String): Observable<SearchMatchResponse>

    @GET("searchteams.php")
    fun getTeams(@Query("t") t: String): Observable<TeamResponse>

    @GET("lookup_all_teams.php")
    fun getTeamsByLeague(@Query("id") id: String): Observable<TeamResponse>

    @GET("searchplayers.php")
    fun getPlayersByTeams(@Query("t") id: String): Observable<PlayerResponse>
}