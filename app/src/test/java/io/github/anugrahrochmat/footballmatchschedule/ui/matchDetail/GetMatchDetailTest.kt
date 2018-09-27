package io.github.anugrahrochmat.footballmatchschedule.ui.matchDetail

import io.github.anugrahrochmat.footballmatchschedule.data.api.ApiInterface
import io.github.anugrahrochmat.footballmatchschedule.data.models.MatchSchedule
import io.github.anugrahrochmat.footballmatchschedule.data.models.MatchScheduleResponse
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
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class GetMatchDetailTest {
    @Mock
    private lateinit var view: MatchDetailView

    @Mock
    private lateinit var api: ApiInterface

    val matchId: String = "582086"
    lateinit var presenter: MatchDetailPresenter

    @Before
    fun setup(){
        RxAndroidPlugins.setInitMainThreadSchedulerHandler({ Schedulers.trampoline() })
        MockitoAnnotations.initMocks(this)

        presenter = MatchDetailPresenter(view)
    }

    @Test
    fun test_getMatchDetail_return_success(){
        val match = mock(MatchSchedule::class.java)
        val matchScheduleResponse = mock(MatchScheduleResponse::class.java)

        Mockito.`when`(api.getMatchDetail(matchId)).thenReturn(Observable.just(matchScheduleResponse))

        presenter.getMatchDetail(matchId)

        launch {
            verify(view).showLoading()
            verify(view).hideLoading()
            verify(view).showMatchDetail(match)
        }
    }

}