package com.hieuwu.justart.presentation.views.custom

import android.content.Context
import android.util.AttributeSet
import android.widget.Button
import android.widget.LinearLayout
import com.hieuwu.justart.R
import com.hieuwu.justart.domain.models.ArtWorkDo

class ErrorView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {
    var refreshButton: Button
    lateinit var refreshClick: () -> Unit

    init {
        val view = inflate(
            context,
            R.layout.layout_error_view, this
        )
        refreshButton = view.findViewById(R.id.refresh_button)
        refreshButton.setOnClickListener {
            refreshClick()
        }
    }

    fun setRefresh(onRefresh: () -> Unit) {
        refreshClick = onRefresh
    }
}