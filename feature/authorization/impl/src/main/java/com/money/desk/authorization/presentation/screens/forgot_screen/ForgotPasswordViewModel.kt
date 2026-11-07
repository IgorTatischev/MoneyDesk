package com.money.desk.authorization.presentation.screens.forgot_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.money.desk.authorization.domain.use_cases.ResetPasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class ForgotPasswordViewModel @Inject constructor(
    private val resetPasswordUseCase: ResetPasswordUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(ForgotState())
    val state = _state.asStateFlow()

    private val _uiEffect = MutableSharedFlow<UiEffect>()
    val uiEffect = _uiEffect.asSharedFlow()

    fun resetPassword() = with(_state.value) {
        _state.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            resetPasswordUseCase(emailText)
                .onSuccess {
                    _uiEffect.emit(UiEffect.NavigateToSignInScreen)
                }
                .onFailure { e ->
                    _state.update { it.copy(isLoading = false) }
                    _uiEffect.emit(UiEffect.ShowSnackbar(e.message))
                }
        }
    }

    fun changeEmailText(newValue: String) {
        _state.update { it.copy(emailText = newValue) }
    }
}