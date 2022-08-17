package com.hieuwu.justart.presentation.views.custom

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.hieuwu.justart.R

class ErrorView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {
    init {
        inflate(
            context,
            R.layout.layout_error_view, this
        )
    }

}