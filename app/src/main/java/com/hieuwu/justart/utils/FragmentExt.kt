package com.hieuwu.justart.utils

import android.view.Gravity
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.core.view.updatePadding
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Explode
import androidx.transition.Slide
import com.google.android.material.appbar.AppBarLayout
import com.hieuwu.justart.R
import com.hieuwu.justart.presentation.views.FAST_OUT_LINEAR_IN
import com.hieuwu.justart.presentation.views.LARGE_COLLAPSE_DURATION
import com.hieuwu.justart.presentation.views.LARGE_EXPAND_DURATION
import com.hieuwu.justart.presentation.views.LINEAR_OUT_SLOW_IN
import com.hieuwu.justart.presentation.views.animation.helper.SpaceDecoration
import com.hieuwu.justart.presentation.views.animation.helper.plusAssign
import com.hieuwu.justart.presentation.views.animation.helper.transitionTogether
import com.hieuwu.justart.presentation.views.custom.LoadingDialogFragment

const val TAG = "LOADING_DIALOG_TAG"

fun Fragment.showLoading() {
    LoadingDialogFragment().show(parentFragmentManager,TAG)
}

fun Fragment.hideLoading() {
    val dialog = parentFragmentManager.findFragmentByTag(TAG) as DialogFragment
    dialog.dismiss()
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

fun Fragment.setupWindowListener(view: View, toolbar: Toolbar, recyclerView: RecyclerView) {
    val gridPadding = resources.getDimensionPixelSize(R.dimen.spacing_tiny)
    ViewCompat.setOnApplyWindowInsetsListener(view.parent as View) { _, insets ->
        val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
        toolbar.updateLayoutParams<AppBarLayout.LayoutParams> {
            topMargin = systemBars.top
        }
        recyclerView.updatePadding(
            left = gridPadding + systemBars.left,
            right = gridPadding + systemBars.right,
            bottom = gridPadding + systemBars.bottom
        )
        insets
    }
    recyclerView.addItemDecoration(
        SpaceDecoration(resources.getDimensionPixelSize(R.dimen.spacing_tiny))
    )
}