package io.github.anugrahrochmat.footballmatchschedule.ui.matchDetail

import io.github.anugrahrochmat.footballmatchschedule.data.api.ApiInterface
import io.github.anugrahrochmat.footballmatchschedule.data.models.Team
import io.github.anugrahrochmat.footballmatchschedule.data.models.TeamResponse
import io.github.anugrahrochmat.footballmatchschedule.ui.matchActivity.matchDetail.MatchDetailPresenter
import io.github.anugrahrochmat.footballmatchschedule.ui.matchActivity.matchDetail.MatchDetailView
import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.experimental.launch
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations


@RunWith(JUnit4::class)
class GetTeamBadgesTest {
    @Mock
    private lateinit var view: MatchDetailView

    @Mock
    private lateinit var api: ApiInterface

    val homeTeamName: String = "Milan"
    val awayTeamName: String = "Roma"
    lateinit var presenter: MatchDetailPresenter

    @Before
    fun setup(){
        RxAndroidPlugins.setInitMainThreadSchedulerHandler({ Schedulers.trampoline() })
        MockitoAnnotations.initMocks(this)

        presenter = MatchDetailPresenter(view)
    }

    @Test
    fun test_getTeamBadges_return_success(){
        val team = Mockito.mock(Team::class.java)
        val teamResponse = Mockito.mock(TeamResponse::class.java)

        Mockito.`when`(api.getTeams(homeTeamName)).thenReturn(Observable.just(teamResponse))
        Mockito.`when`(api.getTeams(awayTeamName)).thenReturn(Observable.just(teamResponse))

        presenter.getTeamBadges(homeTeamName, awayTeamName)

        launch {
            Mockito.verify(view).showLoading()
            Mockito.verify(view).hideLoading()
            Mockito.verify(view).loadHomeBadge(team.teamBadge!!)
            Mockito.verify(view).loadAwayBadge(team.teamBadge!!)
        }
    }
}