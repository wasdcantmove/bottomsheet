package uk.co.scottishpower.views.bottomsheet

import android.content.Context
import android.support.design.widget.BottomSheetBehavior
import android.support.design.widget.CoordinatorLayout
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class SafeBottomSheetBehavior<V : View>(
        context: Context,
        attrs: AttributeSet) : BottomSheetBehavior<V>(context, attrs) {

    private var isAfterLayoutChild: Boolean = false

    override fun onInterceptTouchEvent(parent: CoordinatorLayout?, child: V, event: MotionEvent?): Boolean =
            isAfterLayoutChild && super.onInterceptTouchEvent(parent, child, event)

    override fun onTouchEvent(parent: CoordinatorLayout?, child: V, event: MotionEvent?): Boolean =
            isAfterLayoutChild && super.onTouchEvent(parent, child, event)

    override fun onLayoutChild(parent: CoordinatorLayout, child: V, layoutDirection: Int): Boolean =
            super.onLayoutChild(parent, child, layoutDirection).also { isAfterLayoutChild = it }
}
