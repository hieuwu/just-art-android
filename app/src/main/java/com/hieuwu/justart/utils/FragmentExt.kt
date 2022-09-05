package com.hieuwu.justart.utils

import android.view.Gravity
import androidx.fragment.app.Fragment
import androidx.transition.Explode
import androidx.transition.Slide
import com.hieuwu.justart.R
import com.hieuwu.justart.presentation.views.FAST_OUT_LINEAR_IN
import com.hieuwu.justart.presentation.views.LARGE_COLLAPSE_DURATION
import com.hieuwu.justart.presentation.views.LARGE_EXPAND_DURATION
import com.hieuwu.justart.presentation.views.LINEAR_OUT_SLOW_IN
import com.hieuwu.justart.presentation.views.animation.helper.plusAssign
import com.hieuwu.justart.presentation.views.animation.helper.transitionTogether
import com.hieuwu.justart.presentation.views.custom.LoadingDialogFragment

const val TAG = "LOADING_DIALOG_TAG"

fun Fragment.showLoading() {
    LoadingDialogFragment.getInstance().show(parentFragmentManager, TAG)
}

fun Fragment.hideLoading() {
    LoadingDialogFragment.getInstance().dismiss()
}

fun Fragment.setupExitTransition() {
    exitTransition = transitionTogether {
        duration = LARGE_EXPAND_DURATION / 2
        interpolator = FAST_OUT_LINEAR_IN
        // The app bar.
        this += Slide(Gravity.TOP).apply {
            mode = Slide.MODE_OUT
            addTarget(R.id.app_bar)
        }
        // The grid items.
        this += Explode().apply {
            mode = Explode.MODE_OUT
            excludeTarget(R.id.app_bar, true)
        }
    }
}

fun Fragment.setupReEnterTransition() {
    reenterTransition = transitionTogether {
        duration = LARGE_COLLAPSE_DURATION / 2
        interpolator = LINEAR_OUT_SLOW_IN
        // The app bar.
        this += Slide(Gravity.TOP).apply {
            mode = Slide.MODE_IN
            addTarget(R.id.app_bar)
        }
        // The grid items.
        this += Explode().apply {
            // The grid items should start imploding after the app bar is in.
            startDelay = LARGE_COLLAPSE_DURATION / 2
            mode = Explode.MODE_IN
            excludeTarget(R.id.app_bar, true)
        }
    }
}