package wasdcantmove.basetest

import android.app.Activity
import android.support.test.rule.ActivityTestRule
import org.junit.Rule

abstract class BaseActivityTest<T : Activity>(
        @Rule @JvmField val activityRule : ActivityTestRule<T>) : BaseUiTest<T>() {

    override val activity: T
        get() = activityRule.activity
}
