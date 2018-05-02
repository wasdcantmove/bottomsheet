package wasdcantmove.basetest

import android.support.test.espresso.Root
import android.view.WindowManager
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher

class ToastMatcher : TypeSafeMatcher<Root>() {

    override fun describeTo(description: Description) {
        description.appendText("is toast")
    }

    @Suppress("DEPRECATION")
    override fun matchesSafely(root: Root): Boolean =
        (root.windowLayoutParams.get().type == WindowManager.LayoutParams.TYPE_TOAST)
                && root.decorView.let { it.windowToken === it.applicationWindowToken }
}
