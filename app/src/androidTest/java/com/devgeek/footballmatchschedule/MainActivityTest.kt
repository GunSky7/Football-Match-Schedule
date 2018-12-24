package com.devgeek.footballmatchschedule

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.pressBack
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import com.devgeek.footballmatchschedule.R.id.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @Rule
    @JvmField var activityRule = ActivityTestRule(HomeActivity::class.java)

    @Test
    fun testApp() {
        /*Test to check if function search is working*/

        // HomeActivity to SearchActivity
        Thread.sleep(3000)
        onView(withId(home_search))
                .check(matches(isDisplayed()))
        onView(withId(home_search)).perform(click())

        // Check if add to favourite button is working
        Thread.sleep(2000)
        onView(withId(search))
                .check(matches(isDisplayed()))
        onView(withId(search)).perform(typeText("Man United"))

        // Check if recycleview to detailactivity working
        Thread.sleep(3000)
        onView(withId(rv_search))
                .check(matches(isDisplayed()))
        onView(withId(rv_search)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        Thread.sleep(3000)
    }
}