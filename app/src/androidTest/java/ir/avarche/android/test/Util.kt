package ir.avarche.android.test

import android.app.Activity
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry
import androidx.test.runner.lifecycle.Stage

fun ensureThatViewIsOnScreenNow(id:Int) = Espresso.onView(ViewMatchers.withId(id)).check(
    ViewAssertions.matches(ViewMatchers.isDisplayed()))

fun ensureThatViewWithTextIsOnScreenNow(text: String) = Espresso.onView(ViewMatchers.withSubstring(text)).check(
    ViewAssertions.matches(ViewMatchers.isDisplayed()))

fun typeTextOnTextField(id:Int,text:String) = Espresso.onView(ViewMatchers.withId(id)).perform(
    ViewActions.typeText(text))

fun clickOnView(id:Int) = Espresso.onView(ViewMatchers.withId(id)).perform(ViewActions.click())

fun getCurrentActivity(): Activity? {
    var currentActivity: Activity? = null
    getInstrumentation().runOnMainSync { run { currentActivity = ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(
        Stage.RESUMED).elementAtOrNull(0) } }
    return currentActivity
}

val applicationContext
    get() = InstrumentationRegistry.getInstrumentation().targetContext

fun getString(id:Int) = applicationContext.getString(id)