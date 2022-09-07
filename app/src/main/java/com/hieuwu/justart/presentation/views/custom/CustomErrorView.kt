package com.hieuwu.justart.presentation.views.custom

import android.content.Context
import android.util.AttributeSet
import android.widget.Button
import android.widget.LinearLayout
import androidx.core.content.withStyledAttributes
import com.airbnb.lottie.LottieAnimationView
import com.hieuwu.justart.R

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
        val animationView = view.findViewById<LottieAnimationView>(R.id.animation_view)

        context.withStyledAttributes(attrs, R.styleable.ErrorView) {
            animationView.setAnimation(getString(R.styleable.ErrorView_animationFileName))
        }
    }

    fun setRefresh(onRefresh: () -> Unit) {
        refreshClick = onRefresh
    }
}