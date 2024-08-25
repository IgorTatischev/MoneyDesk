package com.money.desk.authorization.presentation.screens.sign_up

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.money.authorization.R
import com.money.common.Resource
import com.money.desk.authorization.domain.model.UserModel
import com.money.desk.authorization.domain.use_cases.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
internal class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
) : ViewModel() {

    private val _signUpState = MutableStateFlow(SignUpState())
    val signUpState = _signUpState.asStateFlow()

    private val _uiEffect = MutableSharedFlow<UiEffect>()
    val uiEffect = _uiEffect.asSharedFlow()

    fun changeLoginText(newValue: String) {
        _signUpState.update { it.copy(loginText = newValue) }
    }

    fun changePasswordText(newValue: String) {
        _signUpState.update { it.copy(passwordText = newValue) }
    }

    fun changeNameText(newValue: String) {
        _signUpState.update { it.copy(nameText = newValue) }
    }

    fun signUp() = with(signUpState.value) {
        signUpUseCase(
            UserModel(
                login = loginText,
                password = passwordText,
                name = nameText,
            )
        ).onEach { result ->
            if (validate()) {
                when (result) {
                    is Resource.Error -> _uiEffect.emit(UiEffect.ShowSnackbar(message = result.message))

                    is Resource.Loading -> _signUpState.update { it.copy(isLoading = true) }

                    is Resource.Success -> {
                        _signUpState.update { it.copy(isLoading = false) }
                        _uiEffect.emit(UiEffect.NavigateToSignInScreen(loginText))
                    }
                }
            } else {
                _uiEffect.emit(UiEffect.ShowSnackbar(resId = R.string.register_error))
            }
        }.launchIn(viewModelScope)
    }

    private fun validate(): Boolean = with(signUpState.value) {
        val emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
        val isEmail = Pattern.compile(emailPattern).matcher(loginText).matches()
        return isEmail && loginText.isNotBlank() && passwordText.isNotBlank() && nameText.isNotBlank()
    }
}