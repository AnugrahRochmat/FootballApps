package io.github.anugrahrochmat.footballmatchschedule.ui

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule
import io.github.anugrahrochmat.footballmatchschedule.R
import io.github.anugrahrochmat.footballmatchschedule.ui.matches.matchSchedule.MatchScheduleFragment
import org.junit.Rule
import org.junit.Test


class MatchDetailActivityTest {
    @Rule
    @JvmField var activityRule = ActivityTestRule(MainActivity::class.java, true, true)

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
    fun testMatchDetailActivity() {
        val listItemPosition = 3

        delay()

        onView(withRecyclerView(MatchScheduleFragment.rvMatchScheduleID).atPosition(listItemPosition))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(withRecyclerView(MatchScheduleFragment.rvMatchScheduleID).atPosition(listItemPosition))
                .perform(ViewActions.click())
        onView(ViewMatchers.withId(R.id.rel_layout_match_detail)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        onView(ViewMatchers.withId(R.id.add_favourite)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(ViewMatchers.withId(R.id.add_favourite)).perform(ViewActions.click())
        onView(ViewMatchers.withId(R.id.add_favourite)).perform(ViewActions.click())
    }
}