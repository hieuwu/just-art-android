package com.hieuwu.justart.presentation.views

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.hieuwu.justart.R
import timber.log.Timber


@SuppressLint("ViewConstructor")
class CollapseParagraphView(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int,
    defStyleRes: Int
) : ConstraintLayout(context, attrs, defStyleAttr, defStyleRes) {
    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : this(context = context, attrs = attrs, 0, 0) {
        inflateLayout()
    }

    private fun inflateLayout() {
        val view =
            LayoutInflater.from(context).inflate(R.layout.custom_collapse_paragraph_view, this)
        val title = view.findViewById<TextView>(R.id.titleText)
        title.setOnClickListener {
            Timber.d("Tittle click")
        }

    }
}