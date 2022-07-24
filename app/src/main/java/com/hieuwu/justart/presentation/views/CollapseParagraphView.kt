package com.hieuwu.justart.presentation.views

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.withStyledAttributes
import androidx.databinding.DataBindingUtil
import com.hieuwu.justart.R
import com.hieuwu.justart.databinding.CustomCollapseParagraphViewBinding


@SuppressLint("ViewConstructor")
class CollapseParagraphView(
    context: Context,
    attrs: AttributeSet?,
) : ConstraintLayout(context, attrs) {

    private var title: String? = null
    private var content: String? = null

    private var titleTextView: TextView? = null
    private var contentTextView: TextView? = null

    init {
        val view = inflate(
            context,
            R.layout.custom_collapse_paragraph_view, this
        )

        titleTextView = view.findViewById(R.id.titleText)
        contentTextView = view.findViewById(R.id.paragraphText)

        context.withStyledAttributes(attrs, R.styleable.CollapseParagraphView) {
            titleTextView?.text = getString(R.styleable.CollapseParagraphView_titleValue)
            contentTextView?.text = getString(R.styleable.CollapseParagraphView_contentValue)
        }
    }

    fun setTitle(text: String?) {
        text?.let {
            titleTextView?.text = text
        }
    }

    fun setContent(text: String?) {
        text?.let {
            contentTextView?.text = text
        }
    }
}