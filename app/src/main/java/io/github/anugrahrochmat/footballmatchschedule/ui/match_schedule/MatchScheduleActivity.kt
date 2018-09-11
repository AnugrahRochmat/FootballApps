package io.github.anugrahrochmat.footballmatchschedule.ui.match_schedule

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import io.github.anugrahrochmat.footballmatchschedule.R
import io.github.anugrahrochmat.footballmatchschedule.data.api.ApiClient
import io.github.anugrahrochmat.footballmatchschedule.data.api.ApiInterface
import io.github.anugrahrochmat.footballmatchschedule.data.model.MatchSchedule
import io.github.anugrahrochmat.footballmatchschedule.ui.match_detail.MatchDetailActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_match_schedule.*
import org.jetbrains.anko.startActivity


class MatchScheduleActivity : AppCompatActivity() {
    private val TAG: String = MatchScheduleActivity::class.java.simpleName
    private val PREV: String = "prev"
    private val NEXT: String = "next"
    private val ID_LEAGUE: String = "4332"
    private var matches: List<MatchSchedule> = mutableListOf()
    private lateinit var adapter: MatchScheduleAdapter
//    private lateinit var rvMatchSchedule: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_schedule)

        rv_match_schedule.layoutManager = LinearLayoutManager(this)
        initData(PREV)

        bottom_nav_menu.setOnNavigationItemSelectedListener {item ->
            when (item.itemId) {
                R.id.btn_last_match -> {
                    initData(PREV)
                    true
                }
                R.id.btn_next_match -> {
                    initData(NEXT)
                    true
                }
                else -> false
            }
        }
    }

    private fun initData(scheduleState: String) {
        val apiServices = ApiClient.client.create(ApiInterface::class.java)
        when (scheduleState) {
            PREV -> apiServices.getPreviousSchedules(ID_LEAGUE)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            {
                                matches = it.matchSchedules!!
                                rv_match_schedule.adapter = MatchScheduleAdapter(matches){
                                    startActivity<MatchDetailActivity>("match" to it)
                                }
                                adapter.notifyDataSetChanged()
                            },
                            {
                                error -> Log.e(TAG, error.message)
                            }
                    )
            NEXT -> apiServices.getNextSchedules(ID_LEAGUE)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            {
                                matches = it.matchSchedules!!
                                rv_match_schedule.adapter = MatchScheduleAdapter(matches){
                                    startActivity<MatchDetailActivity>("match" to it)
                                }
                                adapter.notifyDataSetChanged()
                            },
                            {
                                error -> Log.e(TAG, error.message)
                            }
                    )
        }
    }

}


