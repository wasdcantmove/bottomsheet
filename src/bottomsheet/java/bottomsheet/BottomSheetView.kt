package uk.co.scottishpower.views.bottomsheet

import android.content.Context
import android.content.res.TypedArray
import android.support.annotation.StringRes
import android.support.design.widget.BottomSheetBehavior
import android.text.method.LinkMovementMethod
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import uk.co.scottishpower.view.R
import kotlin.properties.Delegates


class BottomSheetView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0) : LinearLayout(context, attrs, defStyleAttr) {

    val state: Int
        get() = BottomSheetBehavior.from(this).state

    var headerText: String by Delegates.observable(initialValue = "") { _, _, value ->
        header?.text = value
    }

    var bodyText: String?
        get() = body?.text?.toString()
        set(value) {
            body?.text = value
        }

    var imageView: BottomSheetType by Delegates.observable(initialValue = BottomSheetType.NONE) { _, _, value ->
        body?.setCompoundDrawablesWithIntrinsicBounds(0, value.iconRes, 0, 0)
    }

    @setparam:StringRes
    var titleRes: Int
        @Deprecated(message = "Write-only property", level = DeprecationLevel.ERROR) get() = 0
        set(value) {
            headerText = context.getString(value)
        }

    @setparam:StringRes
    var messageRes: Int
        @Deprecated(message = "Write-only property", level = DeprecationLevel.ERROR) get() = 0
        set(value) {
            bodyText = context.getString(value)
        }

    val header: TextView? by lazy {
        findViewById(R.id.header_text)
    }

    val body: TextView? by lazy {
        findViewById(R.id.body_text)
    }

    val chevron: ImageView? by lazy {
        findViewById(R.id.bottom_sheet_chevron)
    }

    init {
        init(context)
        attrs?.let {
            context.theme.obtainStyledAttributes(it, R.styleable.Bottom_sheet, 0, 0)
        }?.also {
            initAttributes(it)
            it.recycle()
        }
    }

    private fun init(context: Context) {
        inflate(context, R.layout.view_bottom_sheet, this)
        imageView = BottomSheetType.NONE
    }

    fun rotateChevron(chevron: ImageView, orientation: Boolean) {
        chevron.animate().rotation(if (orientation) -90f else 90f).start()
    }

    private fun initAttributes(array: TypedArray) {
        headerText = array.getString(R.styleable.Bottom_sheet_header_text) ?: ""
        bodyText = array.getString(R.styleable.Bottom_sheet_body_text) ?: ""
        imageView = BottomSheetType[array.getInteger(R.styleable.Bottom_sheet_icon_type, 0)]
                ?: BottomSheetType.NONE
        body?.movementMethod = LinkMovementMethod.getInstance()
    }

    fun showPlain(@StringRes title: Int, @StringRes message: Int) {
        showMessage(BottomSheetType.NONE, title, message)
    }

    fun showError(@StringRes title: Int, @StringRes message: Int) {
        showMessage(BottomSheetType.ERROR, title, message)
    }

    private fun showMessage(panelType: BottomSheetType, @StringRes title: Int, @StringRes message: Int) {
        imageView = panelType
        titleRes = title
        messageRes = message
        visibility = View.VISIBLE
    }

    fun showPlain(title: String, message: String) {
        showMessage(BottomSheetType.NONE, title, message)
    }

    fun showError(title: String, message: String) {
        showMessage(BottomSheetType.ERROR, title, message)
    }

    private fun showMessage(panelType: BottomSheetType, title: String, message: String) {
        imageView = panelType
        this.headerText = title
        this.bodyText = message
        visibility = View.VISIBLE
    }

    fun addBottomSheetView() {

        BottomSheetBehavior.from(this).apply {
            state = BottomSheetBehavior.STATE_COLLAPSED

            setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {

                var oldState: Int = state

                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    when (newState) {
                        BottomSheetBehavior.STATE_EXPANDED -> true
                        BottomSheetBehavior.STATE_COLLAPSED -> false
                        else -> when (oldState) {
                            BottomSheetBehavior.STATE_EXPANDED -> false
                            BottomSheetBehavior.STATE_COLLAPSED -> true
                            else -> null
                        }
                    }?.let { orientation ->
                        chevron?.let { rotateChevron(it, orientation) }
                    }
                    oldState = newState
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) = Unit
            })
            this@BottomSheetView.setOnClickListener {
                state = when (state) {
                    BottomSheetBehavior.STATE_EXPANDED -> BottomSheetBehavior.STATE_COLLAPSED
                    else -> BottomSheetBehavior.STATE_EXPANDED
                }
            }
        }
    }

    fun handleBackPressed(): Boolean = BottomSheetBehavior.from(this).run {
        when (state) {
            BottomSheetBehavior.STATE_COLLAPSED -> false
            else -> {
                state = BottomSheetBehavior.STATE_COLLAPSED
                true
            }
        }
    }
}
