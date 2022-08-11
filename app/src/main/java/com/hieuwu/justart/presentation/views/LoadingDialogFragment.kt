package com.hieuwu.justart.presentation.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.hieuwu.justart.R

class LoadingDialogFragment : DialogFragment() {

    companion object {
        @Volatile
        private var INSTANCE: LoadingDialogFragment? = null

        fun getInstance(): LoadingDialogFragment {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = LoadingDialogFragment()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        isCancelable = false
        return inflater.inflate(R.layout.layout_loading_dialog, container, false)
    }
}

