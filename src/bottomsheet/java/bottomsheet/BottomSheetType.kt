package uk.co.scottishpower.views.bottomsheet

import android.support.annotation.DrawableRes
import uk.co.scottishpower.view.R

enum class BottomSheetType(@DrawableRes val iconRes: Int) {
    NONE(0),
    ERROR(R.drawable.ic_dialog_error);

    companion object {
        operator fun get(ordinal: Int): BottomSheetType? =
                values().getOrNull(ordinal)
    }
}
