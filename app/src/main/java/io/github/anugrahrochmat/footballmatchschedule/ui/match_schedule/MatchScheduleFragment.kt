package io.github.anugrahrochmat.footballmatchschedule.ui.match_schedule

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.anugrahrochmat.footballmatchschedule.data.api.ApiClient
import io.github.anugrahrochmat.footballmatchschedule.data.api.ApiInterface
import io.github.anugrahrochmat.footballmatchschedule.data.models.MatchSchedule
import io.github.anugrahrochmat.footballmatchschedule.ui.MainActivity
import io.github.anugrahrochmat.footballmatchschedule.ui.match_detail.MatchDetailActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.startActivity

class MatchScheduleFragment : Fragment(), AnkoComponent<Context> {

    private val TAG: String = MainActivity::class.java.simpleName
    private var matches: List<MatchSchedule> = mutableListOf()
    private lateinit var adapter: MatchScheduleAdapter
    private lateinit var rvMatchSchedule: RecyclerView

    companion object{
        const val PREV: String = "prev"
        const val NEXT: String = "next"
        private const val ID_LEAGUE: String = "4332"
        private const val MATCH_SCHEDULE_STATE = "MATCH_SCHEDULE_STATE"

        fun newInstance(match_schedule_state: String):MatchScheduleFragment{
            val args = Bundle()
            args.putString(MATCH_SCHEDULE_STATE, match_schedule_state)

            val fragment = MatchScheduleFragment()
            fragment.arguments = args

            return fragment
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val matchState = arguments!!.getString(MATCH_SCHEDULE_STATE)
        initData(matchState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return createView(AnkoContext.create(ctx))
    }

    override fun createView(ui: AnkoContext<Context>): View = with(ui) {
        linearLayout {
            lparams(matchParent, wrapContent)

            rvMatchSchedule = recyclerView {
                lparams(matchParent, wrapContent)
                layoutManager = LinearLayoutManager(ctx)
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
                                rvMatchSchedule.adapter = MatchScheduleAdapter(matches){
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
    }
}