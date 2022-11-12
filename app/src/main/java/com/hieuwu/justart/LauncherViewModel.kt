package com.hieuwu.justart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hieuwu.justart.data.prefs.PreferenceStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import com.hieuwu.justart.LauncherNavigationAction.NavigateToMainAction
import com.hieuwu.justart.LauncherNavigationAction.NavigateToOnboardingAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@HiltViewModel
class LauncherViewModel @Inject constructor(
    preferenceStorage: PreferenceStorage
) : ViewModel() {
    val launchDestination =
        preferenceStorage.onboardingCompleted.map { result ->
            if (result) {
                NavigateToMainAction
            } else {
                NavigateToOnboardingAction
            }
        }.flowOn(Dispatchers.IO)
}

sealed class LauncherNavigationAction {
    object NavigateToOnboardingAction : LauncherNavigationAction()
    object NavigateToMainAction : LauncherNavigationAction()
}