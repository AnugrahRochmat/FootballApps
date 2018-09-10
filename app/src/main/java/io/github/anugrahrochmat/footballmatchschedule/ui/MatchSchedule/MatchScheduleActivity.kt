package io.github.anugrahrochmat.footballmatchschedule.ui.MatchSchedule

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import io.github.anugrahrochmat.footballmatchschedule.data.api.ApiClient
import io.github.anugrahrochmat.footballmatchschedule.data.api.ApiInterface
import io.github.anugrahrochmat.footballmatchschedule.data.model.MatchSchedule
import io.github.anugrahrochmat.footballmatchschedule.ui.MatchDetail.MatchDetailActivity
import io.github.anugrahrochmat.footballmatchschedule.ui.MatchSchedule.MatchScheduleActivity.MatchActivityUI.Companion.rvMatchScheduleID
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView


class MatchScheduleActivity : AppCompatActivity() {
    private val TAG: String = MatchScheduleActivity::class.java.simpleName
    private val ID_LEAGUE: String = "4332"
    private var matches: List<MatchSchedule> = mutableListOf()
    private lateinit var adapter: MatchScheduleAdapter
    private lateinit var rvMatchSchedule: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MatchActivityUI().setContentView(this)

        rvMatchSchedule = findViewById(rvMatchScheduleID)

        initData()
    }

    private fun initData() {
        val apiServices = ApiClient.client.create(ApiInterface::class.java)
        apiServices.getPreviousSchedules(ID_LEAGUE)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            matches = it.matchSchedules!!
                            rvMatchSchedule.adapter = MatchScheduleAdapter(matches){
                                startActivity<MatchDetailActivity>("match" to it)
                            }
                            adapter.notifyDataSetChanged()
                        },
                        {
                            error -> Log.e(TAG, error.message)
                        }
                )
    }

    class MatchActivityUI : AnkoComponent<MatchScheduleActivity> {
        companion object {
            const val rvMatchScheduleID = 1
        }

        override fun createView(ui: AnkoContext<MatchScheduleActivity>)= with(ui) {
            recyclerView {
                lparams(matchParent, wrapContent)
                id = rvMatchScheduleID
                layoutManager = LinearLayoutManager(ctx)
            }
        }
    }
}


