package com.money.desk.authorization.presentation.screens.sign_in

import androidx.annotation.StringRes
import androidx.core.app.NotificationCompat.MessagingStyle.Message
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.money.authorization.R
import com.money.common.Resource
import com.money.desk.authorization.domain.repository.AuthRepository
import com.money.desk.authorization.domain.use_cases.GoogleSignInUseCase
import com.money.desk.authorization.domain.use_cases.SignInUseCase
import com.money.desk.authorization.presentation.screens.sign_up.SignUpState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class SignInViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val googleSignInUseCase: GoogleSignInUseCase,
) : ViewModel() {

    private val _signInState = MutableStateFlow(SignInState())
    val signInState = _signInState.asStateFlow()

    private val _uiEffect = MutableSharedFlow<UiEffect>()
    val uiEffect = _uiEffect.asSharedFlow()

    fun signIn(login: String, userPassword: String) =
        signInUseCase(
            email = login,
            password = userPassword
        ).onEach { result ->
            when (result) {
                is Resource.Error -> {
                    _signInState.update { it.copy(isLoading = false) }
                    _uiEffect.emit(UiEffect.ShowSnackbar(message = result.message))
                }

                is Resource.Loading -> _signInState.update { it.copy(isLoading = true) }

                is Resource.Success -> _uiEffect.emit(UiEffect.NavigateToMainScreen)
            }
        }.launchIn(viewModelScope)


    fun loginWithGoogle() {
        //todo google login
    }

}


