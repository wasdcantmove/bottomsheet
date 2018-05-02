package wasdcantmove.bottomsheet

import android.content.Intent
import android.support.design.widget.BottomSheetBehavior
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.filters.LargeTest
import android.support.test.runner.AndroidJUnit4
import android.widget.TextView
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import uk.co.scottishpower.test.*
import uk.co.scottishpower.test.spoon.BaseIntentsTest
import uk.co.scottishpower.view.R
import wasdcantmove.basetest.BaseIntentsTest
import wasdcantmove.basetest.intentsTestRule

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
        onView(withId(R.id.bottom_sheet))
                .check(isDisplayed())
    }

    @Test
    @Throws(java.lang.Exception::class)
    fun onClickShouldExpandBottomSheet() {
        onView(withText(R.string.view_important_details))
                .perform(click())

        onView<BottomSheetView>()
                .check<BottomSheetView> {
                    assertEquals(BottomSheetBehavior.STATE_EXPANDED, state)
                }
    }

    @Test
    @Throws(java.lang.Exception::class)
    fun onBackPressedShouldCollapseBottomSheet() {
        onView(withText(R.string.view_important_details))
                .perform(click())

        onView(ViewMatchers.isRoot())
                .perform(ViewActions.pressBack())

        onView<BottomSheetView>()
                .check<BottomSheetView> {
                    assertNotEquals(BottomSheetBehavior.STATE_EXPANDED, state)
                }
    }

    @Test
    @Throws(java.lang.Exception::class)
    fun onBackPressedShouldPerformBack() {
        onView(ViewMatchers.isRoot())
                .perform(ViewActions.pressBack())
        onToast("Back pressed")
                .check(isDisplayed())

    }

    @Test
    @Throws(java.lang.Exception::class)
    fun shouldDisplayTextInHeader() {
        onView(withText(R.string.view_important_details))
                .perform(click())

        onView<BottomSheetView>()
                .perform<BottomSheetView> {
                    headerText = HEADER_TEXT
                }
                .check<BottomSheetView> {
                    assertEquals(HEADER_TEXT, headerText)
                }

    }

    @Test
    @Throws(java.lang.Exception::class)
    fun shouldDisplayTextInBody() {
        onView(withText(R.string.view_important_details))
                .perform(click())

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
        onView(withText(R.string.view_important_details))
                .perform(click())

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