package wasdcantmove.bottomsheet

import android.support.annotation.DrawableRes

enum class BottomSheetType(@DrawableRes val iconRes: Int) {
    NONE(0),
    ERROR(R.drawable.abc_ic_star_black_16dp);

    companion object {
        operator fun get(ordinal: Int): BottomSheetType? =
                values().getOrNull(ordinal)
    }
}
