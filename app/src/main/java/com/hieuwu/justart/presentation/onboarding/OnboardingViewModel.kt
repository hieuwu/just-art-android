package com.hieuwu.justart.presentation.onboarding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hieuwu.justart.data.prefs.PreferenceStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val preferenceStorage: PreferenceStorage
) : ViewModel() {

    private val _navigationAction = MutableLiveData(false)
    val navigationAction: LiveData<Boolean> = _navigationAction

    fun getStartedClicked() {
        viewModelScope.launch {
            preferenceStorage.completeOnboarding(true)
            _navigationAction.value = true
        }
    }
}