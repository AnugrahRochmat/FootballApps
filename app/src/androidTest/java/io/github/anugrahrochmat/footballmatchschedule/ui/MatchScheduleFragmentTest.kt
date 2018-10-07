package io.github.anugrahrochmat.footballmatchschedule.ui

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.pressBack
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import io.github.anugrahrochmat.footballmatchschedule.R
import io.github.anugrahrochmat.footballmatchschedule.R.id.rel_layout_match_detail
import io.github.anugrahrochmat.footballmatchschedule.ui.matchActivity.MatchActivity
import io.github.anugrahrochmat.footballmatchschedule.ui.matchActivity.matchSchedule.MatchScheduleFragment
import org.junit.Rule
import org.junit.Test

class MatchScheduleFragmentTest {
    @Rule
    @JvmField var activityRule = ActivityTestRule(MatchActivity::class.java, true, true)

    private fun withRecyclerView(recyclerViewId: Int): RecyclerViewMatcher {
        return RecyclerViewMatcher(recyclerViewId)
    }

    private fun delay(time: Long = 3000L) {
        try {
            Thread.sleep(time)
        } catch (ex: Exception) {

        }
    }

    @Test
    fun testMatchScheduleRecyclerViewAndClickToMatchDetail() {
        val listItemPosition = 4

        delay()

        onView(withRecyclerView(MatchScheduleFragment.rvMatchScheduleID).atPosition(listItemPosition))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(withRecyclerView(MatchScheduleFragment.rvMatchScheduleID).atPosition(listItemPosition))
                .perform(ViewActions.click())
        onView(withId(rel_layout_match_detail)).check(matches(isDisplayed()))
        pressBack()

        onView(withId(R.id.btn_next_match)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_next_match)).perform(ViewActions.click())
        delay()

        onView(withRecyclerView(MatchScheduleFragment.rvMatchScheduleID).atPosition(listItemPosition))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(withRecyclerView(MatchScheduleFragment.rvMatchScheduleID).atPosition(listItemPosition))
                .perform(ViewActions.click())
        onView(withId(rel_layout_match_detail)).check(matches(isDisplayed()))

        delay()
    }
}