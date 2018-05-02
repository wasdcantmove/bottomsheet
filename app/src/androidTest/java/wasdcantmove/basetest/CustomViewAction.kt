package wasdcantmove.basetest

import android.support.test.espresso.UiController
import android.support.test.espresso.ViewAction
import android.view.View
import org.hamcrest.Matcher
import org.hamcrest.Matchers

class CustomViewAction<T>(val type: Class<T>, val action: T.() -> Unit) : ViewAction {

    override fun getDescription(): String =
            "Custom action on ${type.name}"

    override fun getConstraints(): Matcher<View> =
            Matchers.`is`(Matchers.instanceOf(type))

    override fun perform(uiController: UiController?, view: View?) {
        type.cast(view).action()
    }
}