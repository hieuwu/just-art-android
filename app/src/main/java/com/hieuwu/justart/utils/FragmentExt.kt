package com.hieuwu.justart.utils

import androidx.fragment.app.Fragment
import com.hieuwu.justart.presentation.views.LoadingDialogFragment

const val TAG = "LOADING_DIALOG_TAG"

fun Fragment.showLoading() {
    LoadingDialogFragment.getInstance().show(parentFragmentManager, TAG)
}

fun Fragment.hideLoading() {
    LoadingDialogFragment.getInstance().dismiss()
}