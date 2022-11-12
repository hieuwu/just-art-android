package com.hieuwu.justart.presentation.onboarding

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hieuwu.justart.R
import com.hieuwu.justart.utils.inTransaction
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnboardingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Because we use theme with actionbar
        //supportActionBar!!.hide()
        setContentView(R.layout.activity_onboarding)

        if (savedInstanceState == null) {
            supportFragmentManager.inTransaction {
                add(R.id.fragment_container, OnboardingFragment())
            }
        }
    }
}