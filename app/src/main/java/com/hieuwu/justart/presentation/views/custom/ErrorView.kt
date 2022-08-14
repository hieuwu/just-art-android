package com.hieuwu.justart.presentation.views.custom

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.hieuwu.justart.R

class ErrorView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {
    init {
        val view = inflate(
            context,
            R.layout.layout_error_view, this
        )

//        titleTextView = view.findViewById(R.id.titleText)
//        contentTextView = view.findViewById(R.id.paragraphText)
//        titleLayout = view.findViewById(R.id.titleLayout)
//        arrowButton = view.findViewById(R.id.arrowButton)
//
//        context.withStyledAttributes(attrs, R.styleable.CollapseParagraphView) {
//            titleTextView?.text = getString(R.styleable.CollapseParagraphView_titleValue)
//            contentTextView?.text = getString(R.styleable.CollapseParagraphView_contentValue)
//            showChevron = getBoolean(R.styleable.CollapseParagraphView_showChevron, false)
//        }
//        populateChevron(showChevron)
    }

}