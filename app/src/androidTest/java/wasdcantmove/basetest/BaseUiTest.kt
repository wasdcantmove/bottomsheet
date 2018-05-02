package wasdcantmove.basetest

import android.app.Activity
import it.czerwinski.kotlin.util.Try
import org.junit.Rule

abstract class BaseUiTest<out T : Activity> {

    abstract val activity: T

    @Rule
    @JvmField
    val screenshot = ScreenshotRule()

    fun screenshot(tag: String) {
        Try {
            screenshot(activity, tag)
        }
    }

    operator fun String.unaryPlus() {
        screenshot(this)
    }
}
