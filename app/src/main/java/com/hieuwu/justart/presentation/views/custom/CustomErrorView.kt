package com.hieuwu.justart.presentation.views.custom

import android.content.Context
import android.util.AttributeSet
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.withStyledAttributes
import com.airbnb.lottie.LottieAnimationView
import com.hieuwu.justart.R

private const val DEFAULT_ANIMATION_FILE_NAME = "unplugged_error_view.json"
private const val DEFAULT_ERROR_MESSAGE = "It looks like something wrong. Please try refreshing"


class CustomErrorView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {
    private var refreshButton: Button
    private var errorTextView: TextView
    var refreshClick: () -> Unit = {}

    init {
        val view = inflate(
            context,
            R.layout.layout_error_view, this
        )
        val animationView = view.findViewById<LottieAnimationView>(R.id.animation_view)
        errorTextView = view.findViewById(R.id.errorText)
        refreshButton = view.findViewById(R.id.refresh_button)
        refreshButton.setOnClickListener {
            refreshClick.invoke()
        }

        context.withStyledAttributes(attrs, R.styleable.CustomErrorView) {
            val animationFileName = getString(R.styleable.CustomErrorView_animationFileName)
                ?: DEFAULT_ANIMATION_FILE_NAME
            animationView.setAnimation(animationFileName)
            errorTextView.text = getString(R.styleable.CustomErrorView_errorMessage)
                ?: DEFAULT_ERROR_MESSAGE
        }
    }

    fun setRefresh(onRefresh: () -> Unit) {
        refreshClick = onRefresh
    }
}