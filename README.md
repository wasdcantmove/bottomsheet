# bottomsheet
Easy way to implement a bottomsheet into your app

![Alt Text](https://thumbs.gfycat.com/SecondhandWeepyGourami-size_restricted.gif)


Steps:

1)

Make sure your XML layout a Coordinator layout.

Add bottom sheet to your view and customise as appropriate 
```
<wasdcantmove.bottomsheet.BottomSheetView
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:id="@+id/bottom_sheet"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:linksClickable="true"
    app:behavior_hideable="false"
    app:behavior_peekHeight="@dimen/peek_height"
    app:header_text="bottomsheet"
    app:icon_type="error"
    app:body_text="www.google.com, a clickable link"
    app:layout_behavior="wasdcantmove.bottomsheet.SafeBottomSheetBehavior">

</wasdcantmove.bottomsheet.BottomSheetView>
```

2)

In your activity, include the following:

```
    private val bottomSheetLayout: BottomSheetView? by lazy {
        findViewById<BottomSheetView>(R.id.bottom_sheet)
    }
    
    override fun onResume() {
        super.onResume()
        bottomSheetLayout?.addBottomSheetView()
    }
    
    override fun onBackPressed() {
        if (bottomSheetLayout?.handleBackPressed() != true) {
        }
    }
```

3)

That's it!


