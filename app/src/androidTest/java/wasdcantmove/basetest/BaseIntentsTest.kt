package wasdcantmove.basetest

import android.app.Activity
import android.support.test.espresso.intent.rule.IntentsTestRule
import org.junit.Rule

abstract class BaseIntentsTest<T : Activity>(
        @Rule @JvmField val intentsRule : IntentsTestRule<T>) : BaseActivityTest<T>(intentsRule)
