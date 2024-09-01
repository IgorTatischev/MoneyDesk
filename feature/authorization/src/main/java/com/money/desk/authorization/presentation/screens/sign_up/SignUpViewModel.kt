package com.money.desk.authorization.presentation.screens.sign_up

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.money.authorization.R
import com.money.common.PasswordValidationState
import com.money.common.PasswordValidator
import com.money.common.Resource
import com.money.desk.authorization.domain.model.UserModel
import com.money.desk.authorization.domain.use_cases.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
internal class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
    private val passwordValidator: PasswordValidator = PasswordValidator(),
) : ViewModel() {

    private val _signUpState = MutableStateFlow(SignUpState())
    val signUpState = _signUpState.asStateFlow()

    private val _uiEffect = MutableSharedFlow<UiEffect>()
    val uiEffect = _uiEffect.asSharedFlow()

    private var password by mutableStateOf("")
    @OptIn(ExperimentalCoroutinesApi::class)
    val passwordError =
        snapshotFlow { password }
            .mapLatest { passwordValidator.execute(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = PasswordValidationState()
            )

    fun changeLoginText(newValue: String) {
        _signUpState.update { it.copy(loginText = newValue) }
    }

    fun changePasswordText(newValue: String) {
        password = newValue
        _signUpState.update { it.copy(passwordText = newValue) }
    }

    fun changeNameText(newValue: String) {
        _signUpState.update { it.copy(nameText = newValue) }
    }

    fun signUp() = with(_signUpState.value) {
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

    private fun validate(): Boolean = with(_signUpState.value) {
        val emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
        val isEmail = Pattern.compile(emailPattern).matcher(loginText).matches()
        val result = passwordValidator.execute(passwordText)
        return isEmail && loginText.isNotBlank() && result.successful && nameText.isNotBlank()
    }
}