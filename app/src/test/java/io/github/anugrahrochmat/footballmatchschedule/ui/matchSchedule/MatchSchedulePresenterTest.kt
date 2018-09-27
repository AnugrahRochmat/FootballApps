package io.github.anugrahrochmat.footballmatchschedule.ui.matchSchedule



import io.github.anugrahrochmat.footballmatchschedule.data.api.ApiInterface
import io.github.anugrahrochmat.footballmatchschedule.data.models.MatchSchedule
import io.github.anugrahrochmat.footballmatchschedule.data.models.MatchScheduleResponse
import io.github.anugrahrochmat.footballmatchschedule.ui.matchActivity.matchSchedule.MatchSchedulePresenter
import io.github.anugrahrochmat.footballmatchschedule.ui.matchActivity.matchSchedule.MatchScheduleView
import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.experimental.launch
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class MatchSchedulePresenterTest {
    @Mock
    private lateinit var view: MatchScheduleView

    @Mock
    private lateinit var api: ApiInterface

    val leagueId: String = "4332"
    val prevScheduleState: String = "prev"
    val nextScheduleState: String = "next"
    lateinit var presenter: MatchSchedulePresenter

    @Before
    fun setup(){
        RxAndroidPlugins.setInitMainThreadSchedulerHandler({ Schedulers.trampoline() })
        MockitoAnnotations.initMocks(this)

        presenter = MatchSchedulePresenter(view)
    }

    @Test
    fun test_getPrevMatchSchedule_return_lists(){
        val matches = getMockedMatches(20)
        val matchScheduleResponse = MatchScheduleResponse(matches)

        `when`(api.getPreviousSchedules(leagueId)).thenReturn(Observable.just(matchScheduleResponse))

        presenter.getMatchSchedule(prevScheduleState)

        launch {
            verify(view).showLoading()
            verify(view).hideLoading()
            verify(view).showMatchSchedule(matches)
        }
    }

    @Test
    fun test_getNextMatchSchedule_return_lists(){
        val matches = getMockedMatches(20)
        val matchScheduleResponse = MatchScheduleResponse(matches)

        `when`(api.getNextSchedules(leagueId)).thenReturn(Observable.just(matchScheduleResponse))

        presenter.getMatchSchedule(nextScheduleState)

        launch {
            verify(view).showLoading()
            verify(view).hideLoading()
            verify(view).showMatchSchedule(matches)
        }
    }

    fun getMockedMatches(count: Int) : List<MatchSchedule> {
        val matches = ArrayList<MatchSchedule>()
        for (i in 0..count) {
            val matchSchedule = mock(MatchSchedule::class.java)
            matches.add(matchSchedule)
        }
        return matches
    }
}