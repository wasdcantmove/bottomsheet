package wasdcantmove.bottomsheet

import android.content.Intent
import android.support.design.widget.BottomSheetBehavior
import android.support.test.espresso.Espresso
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.filters.LargeTest
import android.support.test.runner.AndroidJUnit4
import android.widget.TextView
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import wasdcantmove.basetest.*

@RunWith(AndroidJUnit4::class)
@LargeTest
class BottomSheetTests : BaseIntentsTest<BottomSheetActivity>(
        intentsTestRule(launchActivity = false)) {

    @Before
    @Throws(java.lang.Exception::class)
    fun launchActivity() {
        activityRule.launchActivity(Intent())

    }

    @Test
    @Throws(java.lang.Exception::class)
    fun checkBottomSheetIsDisplayed() {

        Espresso.onView(ViewMatchers.withId(R.id.bottom_sheet))
                .check(ViewAssertions.matches(isDisplayed()))
    }

    @Test
    @Throws(java.lang.Exception::class)
    fun onBackPressedShouldCollapseBottomSheet() {

        Espresso.onView(ViewMatchers.withText("bottomsheet"))
                .perform(ViewActions.click())


        Espresso.onView(ViewMatchers.isRoot())
                .perform(ViewActions.pressBack())


        onView<BottomSheetView>()
                .check<BottomSheetView> {
                    Assert.assertNotEquals(BottomSheetBehavior.STATE_EXPANDED, state)
                }
    }


    @Test
    @Throws(java.lang.Exception::class)
    fun shouldDisplayTextInHeader() {

        Espresso.onView(ViewMatchers.withText("bottomsheet"))
                .perform(ViewActions.click())

        onView<BottomSheetView>()
                .perform<BottomSheetView> {
                    headerText = BottomSheetTests.HEADER_TEXT
                }
                .check<BottomSheetView> {
                    assertEquals(HEADER_TEXT, headerText)
                }

    }

    @Test
    @Throws(java.lang.Exception::class)
    fun shouldDisplayTextInBody() {

        Espresso.onView(ViewMatchers.withText("bottomsheet"))
                .perform(ViewActions.click())


        var string: String? = null
        onView<TextView>(withId(R.id.body_text))
                .perform<TextView> {
                    string = BODY_TEXT
                    text = string
                }
        onView<BottomSheetView>()
                .perform<BottomSheetView> {
                    bodyText = string
                }
                .check<BottomSheetView> {
                    assertEquals(BODY_TEXT, bodyText)
                }

    }

    @Test
    @Throws(java.lang.Exception::class)
    fun shouldDisplayClickableTextInBody() {
Thread.sleep(100000)
        Espresso.onView(ViewMatchers.withText("bottomsheet"))
                .perform(ViewActions.click())


        onView<BottomSheetView>()
                .check<BottomSheetView> {
                    assertEquals(BODY_TEXT, bodyText)
                }
    }

    companion object {
        const val HEADER_TEXT: String = "hello header"
        const val BODY_TEXT: String = "www.google.com, a clickable link"
    }
}