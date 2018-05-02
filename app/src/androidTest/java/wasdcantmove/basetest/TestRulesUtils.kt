@file:JvmName("AndroidTestUtils")
@file:JvmMultifileClass

package wasdcantmove.basetest

import android.app.Activity
import android.support.annotation.StringRes
import android.support.test.espresso.Espresso
import android.support.test.espresso.Root
import android.support.test.espresso.ViewInteraction
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.intent.rule.IntentsTestRule
import android.support.test.espresso.matcher.ViewMatchers
import android.view.View
import org.hamcrest.Matcher
import org.hamcrest.Matchers.*
import org.junit.Assert

fun onToast(@StringRes text: Int): ViewInteraction = Espresso
        .onView(ViewMatchers.withText(text))
        .inRoot(isToast())

fun onToast(text: String): ViewInteraction = Espresso
        .onView(ViewMatchers.withText(text))
        .inRoot(isToast())

fun isToast(): Matcher<Root> = ToastMatcher()

inline fun <reified T> ViewInteraction.perform(noinline action: T.() -> Unit): ViewInteraction =
        perform(CustomViewAction(T::class.java, action))

fun ViewInteraction.check(matcher: Matcher<in View>): ViewInteraction =
        check(ViewAssertions.matches(matcher))

inline fun <reified T> ViewInteraction.check(noinline assertion: T.() -> Unit): ViewInteraction =
        check { view, _ ->
            when (view) {
                is T -> view.assertion()
                else -> Assert.fail("View is not a ${T::class.java.name}")
            }
        }

inline fun <reified T> onView(vararg matchers: Matcher<in View>): ViewInteraction = Espresso
        .onView(allOf(`is`(instanceOf(T::class.java)), *matchers))

inline fun <reified T> onView(): ViewInteraction = Espresso
        .onView(`is`(instanceOf(T::class.java)))

inline fun <reified T : Activity> intentsTestRule(
        initialTouchMode: Boolean = false,
        launchActivity: Boolean = true) =
        IntentsTestRule(T::class.java, initialTouchMode, launchActivity)
