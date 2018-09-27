package io.github.anugrahrochmat.footballmatchschedule.ui

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import io.github.anugrahrochmat.footballmatchschedule.R.id.*
import io.github.anugrahrochmat.footballmatchschedule.ui.matchActivity.MatchActivity
import io.github.anugrahrochmat.footballmatchschedule.ui.matchActivity.matchSchedule.MatchScheduleFragment.Companion.progressBarID
import io.github.anugrahrochmat.footballmatchschedule.ui.matchActivity.matchSchedule.MatchScheduleFragment.Companion.rvMatchScheduleID
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MatchActivityTest {
    @Rule
    @JvmField var activityRule = ActivityTestRule(MatchActivity::class.java, true, true)

    @Test
    fun testPreviousScheduleFragmentBehaviour() {
        // Match Schedule
        onView(withId(progressBarID)).check(matches(isDisplayed()))
        onView(withId(rvMatchScheduleID)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(bottom_navigation)).check(matches(isDisplayed()))
        onView(withId(btn_last_match)).check(matches(isDisplayed()))

        onView(withId(btn_next_match)).check(matches(isDisplayed()))
        onView(withId(btn_next_match)).perform(click())
        onView(withId(rvMatchScheduleID)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))

        onView(withId(btn_favourites)).check(matches(isDisplayed()))
        onView(withId(btn_favourites)).perform(click())
    }
}