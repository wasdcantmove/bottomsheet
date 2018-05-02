package uk.co.scottishpower.views.bottomsheet

import android.content.Intent
import android.test.suitebuilder.annotation.LargeTest
import android.widget.TextView
import java.views.bottomsheet.BottomSheetActivity

@RunWith(AndroidJUnit4::class)
@LargeTest
class BottomSheetTests : BaseIntentsTest<BottomSheetActivity>(
        intentsTestRule(launchActivity = false)) {

    @Before
    @Throws(java.lang.Exception::class)
    fun launchActivity() {
        activityRule.launchActivity(Intent())

        +"activity_loaded"
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

        +"bottom_sheet_expanded"

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

        +"bottom_sheet_expanded"

        onView(ViewMatchers.isRoot())
                .perform(ViewActions.pressBack())

        +"bottom_sheet_collapsed"

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

        +"back_pressed"
    }

    @Test
    @Throws(java.lang.Exception::class)
    fun shouldDisplayTextInHeader() {
        onView(withText(R.string.view_important_details))
                .perform(click())

        +"bottom_sheet_expanded"

        onView<BottomSheetView>()
                .perform<BottomSheetView> {
                    headerText = BottomSheetTests.HEADER_TEXT
                }
                .check<BottomSheetView> {
                    assertEquals(HEADER_TEXT, headerText)
                }

        +"bottom_sheet_expanded_text_changed"
    }

    @Test
    @Throws(java.lang.Exception::class)
    fun shouldDisplayTextInBody() {
        onView(withText(R.string.view_important_details))
                .perform(click())

        +"bottom_sheet_expanded"

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

        +"bottom_sheet_expanded_text_changed"
    }

    @Test
    @Throws(java.lang.Exception::class)
    fun shouldDisplayClickableTextInBody() {
        onView(withText(R.string.view_important_details))
                .perform(click())

        +"bottom_sheet_expanded"

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