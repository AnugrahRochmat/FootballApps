package io.github.anugrahrochmat.footballmatchschedule.ui

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.pressBack
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule
import io.github.anugrahrochmat.footballmatchschedule.R
import io.github.anugrahrochmat.footballmatchschedule.ui.favourites.matchFavourites.MatchFavouritesFragment
import io.github.anugrahrochmat.footballmatchschedule.ui.matches.matchSchedule.MatchScheduleFragment
import org.junit.Rule
import org.junit.Test

class MatchFavouriteFragmentTest {
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
    fun testFavouriteRecyclerView() {
        val listItemPosition = 0

        delay()

        onView(withRecyclerView(MatchScheduleFragment.rvMatchScheduleID).atPosition(listItemPosition))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(withRecyclerView(MatchScheduleFragment.rvMatchScheduleID).atPosition(listItemPosition))
                .perform(ViewActions.click())
        onView(ViewMatchers.withId(R.id.add_favourite)).perform(ViewActions.click())
        pressBack()

        onView(ViewMatchers.withId(R.id.btn_favourites)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(ViewMatchers.withId(R.id.btn_favourites)).perform(ViewActions.click())

        onView(withRecyclerView(MatchFavouritesFragment.rvMatchFavouritesID).atPosition(listItemPosition))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(withRecyclerView(MatchFavouritesFragment.rvMatchFavouritesID).atPosition(listItemPosition))
                .perform(ViewActions.click())
        onView(ViewMatchers.withId(R.id.rel_layout_match_detail)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        onView(ViewMatchers.withId(R.id.add_favourite)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(ViewMatchers.withId(R.id.add_favourite)).perform(ViewActions.click())
        pressBack()
    }
}