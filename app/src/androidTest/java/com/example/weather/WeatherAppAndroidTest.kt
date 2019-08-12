package com.example.weather

import android.content.Intent
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.rule.GrantPermissionRule
import androidx.test.runner.AndroidJUnit4
import com.example.weather.view.MainActivity
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@LargeTest
class WeatherActivityTest {

    lateinit var webServer: MockWebServer

    @Rule
    @JvmField
    var activityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    @Rule
    @JvmField
    var permissionRule: GrantPermissionRule =
        GrantPermissionRule.grant(android.Manifest.permission.ACCESS_FINE_LOCATION)

    @Before
    fun initValidString() {
        webServer = MockWebServer()
        webServer.start(8080)
    }

    @Test
    fun testGoldenFlow() {

        webServer.setDispatcher(MockServerDispatcher().ErrorDispatcher())
        activityRule.launchActivity(Intent())
        Espresso.onView(withId(R.id.tv_error)).check(matches(isDisplayed()))
        Espresso.onView(withId(R.id.tv_retry)).check(matches(isDisplayed()))

        webServer.setDispatcher(MockServerDispatcher().RequestDispatcher())
        Espresso.onView(withId(R.id.tv_retry)).perform(click())
        onView(isRoot()).perform(waitId(R.id.tv_current_location, 5000))
        Espresso.onView(withId(R.id.tv_current_location)).check(matches(isDisplayed()))
    }

    @After
    @Throws(Exception::class)
    fun tearDown() {
        webServer.shutdown()
    }

}