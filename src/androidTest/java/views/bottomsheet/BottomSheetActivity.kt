package java.views.bottomsheet

import android.app.Activity
import android.os.Bundle
import uk.co.scottishpower.views.bottomsheet.BottomSheetView


class BottomSheetActivity : Activity() {

    private val bottomSheetLayout: BottomSheetView? by lazy {
        findViewById(R.id.bottom_sheet)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_sheet_test)
    }

    override fun onResume() {
        super.onResume()
        bottomSheetLayout?.addBottomSheetView()
    }

    override fun onBackPressed() {
        if (bottomSheetLayout?.handleBackPressed() != true) {
            toast("Back pressed").show()
        }
    }
}

