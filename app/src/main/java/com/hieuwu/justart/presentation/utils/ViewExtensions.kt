package com.hieuwu.justart.presentation.utils

import android.view.View
import android.view.ViewTreeObserver
import android.view.inputmethod.InputMethodManager
import androidx.core.content.getSystemService

fun View.focusAndShowKeyboard() {
    /**
     * This is to be called when the window already has focus.
     */
    fun View.showTheKeyboardNow() {
        if (isFocused) {
            post {
                // We still post the call, just in case we are being notified of the windows focus
                // but InputMethodManager didn't get properly setup yet.
                val imm = context.getSystemService<InputMethodManager>()
                imm?.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
            }
        }
    }

    requestFocus()
    if (hasWindowFocus()) {
        // No need to wait for the window to get focus
        showTheKeyboardNow()
    } else {
        // We need to wait until the window gets focus
        viewTreeObserver.addOnWindowFocusChangeListener {
            object : ViewTreeObserver.OnWindowFocusChangeListener {
                override fun onWindowFocusChanged(p0: Boolean) {
                    // This notification will arrive just before the InputMethodManager gets set up
                    if (hasFocus()) {
                        this@focusAndShowKeyboard.showTheKeyboardNow()
                        // It's very important to remove this listener once we are done
                        viewTreeObserver.removeOnWindowFocusChangeListener(this)
                    }
                }
            }
        }
    }
}