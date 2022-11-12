package com.hieuwu.justart

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.hieuwu.justart.presentation.onboarding.OnboardingActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LauncherActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        val viewModel: LauncherViewModel by viewModels()

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.launchDestination.stateIn(lifecycleScope).collect { action ->
                    when (action) {
                        is LauncherNavigationAction.NavigateToMainAction -> startActivity(
                            Intent(this@LauncherActivity, MainActivity::class.java)
                        )
                        is LauncherNavigationAction.NavigateToOnboardingAction -> startActivity(
                            Intent(this@LauncherActivity, OnboardingActivity::class.java)
                        )
                    }
                    finish()
                }
            }
        }
    }
}