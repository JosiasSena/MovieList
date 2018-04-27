package com.josiassena.movielist.app_helpers.custom_views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.josiassena.movielist.R
import kotlinx.android.synthetic.main.main_list_header_custom_view.view.*

/**
 * @author Josias Sena
 */
class MainListHeaderViews : LinearLayout {

    private var headerTitle: String? = null

    constructor(context: Context) : super(context) {
        init(null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {
        inflate(context, R.layout.main_list_header_custom_view, this)

        val typedArray = context.theme.obtainStyledAttributes(attrs,
                R.styleable.MainListHeaderViews, 0, 0)

        try {
            headerTitle = typedArray.getString(R.styleable.MainListHeaderViews_header_title)
        } finally {
            typedArray.recycle()
        }

        tvHeaderTitle?.text = headerTitle
    }

    fun setHeaderTitle(title: String) {
        headerTitle = title
        tvHeaderTitle?.text = title

        invalidate()
        requestLayout()
    }

    fun setSeeAllOnClickListener(onclickListener: View.OnClickListener) {
        tvSeeAll?.setOnClickListener(onclickListener)
    }

}