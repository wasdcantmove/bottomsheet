@file:JvmName("AndroidTestUtils")
@file:JvmMultifileClass

package wasdcantmove.basetest

import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import android.net.Uri
import android.support.test.espresso.intent.Intents
import android.support.test.espresso.intent.Intents.times
import android.support.test.espresso.intent.OngoingStubbing
import android.support.test.espresso.intent.matcher.IntentMatchers.*
import android.support.test.espresso.intent.rule.IntentsTestRule
import android.support.test.rule.ActivityTestRule
import org.hamcrest.BaseMatcher
import org.hamcrest.CoreMatchers
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.*
import rx.Single

inline fun <reified T : Activity> activityTestRule(
        initialTouchMode: Boolean = false,
        launchActivity: Boolean = true) =
        ActivityTestRule(T::class.java, initialTouchMode, launchActivity)

inline fun <reified T : Activity> intentsTestRule(
        initialTouchMode: Boolean = false,
        launchActivity: Boolean = true) =
        IntentsTestRule(T::class.java, initialTouchMode, launchActivity)

fun <T> Single<T>.waitForResult(): T =
        toBlocking().value()

inline fun <reified T> hasComponent(): Matcher<Intent> =
        hasComponent(T::class.java.name)

fun OngoingStubbing.respondWith(resultCode: Int, resultData: Intent) =
        respondWith(Instrumentation.ActivityResult(resultCode, resultData))

inline fun <reified T> intending(): OngoingStubbing =
        Intents.intending(hasComponent<T>())

inline fun <reified T> intended() =
        Intents.intended(hasComponent<T>())

inline fun <reified T> intended(vararg extras: Pair<String, Any>) =
        extras.map { (key, value) -> hasExtra(equalTo(key), equalTo(value)) }
                .toTypedArray()
                .let { matchers -> Intents.intended(allOf(hasComponent<T>(), *matchers)) }

inline fun <reified T> intended(vararg matchers: Matcher<Intent>) =
        Intents.intended(allOf(hasComponent<T>(), *matchers))

inline fun <reified T> notIntended() =
        Intents.intended(hasComponent<T>(), times(0))

fun intendingExternal(): OngoingStubbing =
        Intents.intending(CoreMatchers.not(isInternal()))

fun intendedView(uri: Uri) =
        Intents.intended(allOf(hasAction(CoreMatchers.equalTo(Intent.ACTION_VIEW)), hasData(uri)))


/**
 *
 * TEMPORARY MATCHERS for Cmt Landing
 *
 * TODO - Slawek to make into more functional
 *
 **/

fun <T> matchesIntentCustom(kvec: KVExtraComparer<T>): BaseMatcher<Intent> {
    return object : BaseMatcher<Intent>() {

        override fun describeTo(description: Description?) {
            description?.appendText("Intent doesn't contain values when comparing using custom comparator.")
            description?.appendText("\nKVEC:\t$kvec")
        }

        override fun describeMismatch(item: Any?, description: Description?) {

            (item as? Intent)
                    ?.extras
                    ?.containsKey(kvec.key)
                    ?.let { keyPresent ->
                        if(!keyPresent) {
                            description?.appendText("Matched intent does not contain key '${kvec.key}'")
                        }
                    }

            ((item as? Intent)
                    ?.extras
                    ?.get(kvec.key) as? T)
                    ?.let {
                        kvec.produceStringToDescribeMismatch(it)
                    }?.let {
                        description?.appendText(it)
                    }

        }

        override fun matches(item: Any?): Boolean =
                ((item as? Intent)
                        ?.extras
                        ?.get(kvec.key) as? T)
                        ?.let {
                            kvec.compareWith(it)
                        } ?: false

    }
}

class KVExtraComparer<in V>(
        val key: String,
        private val value: V,
        private val comparator: (V, V) -> Boolean) {

    fun compareWith(other: V): Boolean {
        return comparator.invoke(value, other)
    }

    override fun toString(): String {
        return "KVExtraComparer(key='$key', value=$value)"
    }

    fun produceStringToDescribeMismatch(otherElement: V): String? {
        return when {
            !comparator.invoke(value, otherElement) -> "Expected: $value\nActual: $otherElement"
            else -> null
        }
    }


}


inline fun <reified T, reified M> intended(vararg extras: KVExtraComparer<M>) =
        extras.map {
            matchesIntentCustom(it)
        }.toTypedArray()
                .let { matchers ->
                    Intents.intended(allOf(hasComponent<T>(), *matchers))
                }