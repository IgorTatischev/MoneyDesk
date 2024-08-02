package com.money.desk.authorization.presentation.screens.sign_in

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.money.authorization.R
import com.money.desk.authorization.domain.repository.AuthRepository
import com.money.desk.authorization.domain.use_cases.SignInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class SignInViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase
) : ViewModel() {

    private val _uiEffect = MutableSharedFlow<UiEffect>()
    val uiEffect = _uiEffect.asSharedFlow()

    fun signIn()  {
        viewModelScope.launch(Dispatchers.IO) {
//            signInUseCase(
//                login = loginText,
//                userPassword = passwordText
//            ).onFailure {
//                _uiEffect.emit(UiEffect.ShowSnackbar(R.string.login_error))
//            }.onSuccess {
//                _uiEffect.emit(UiEffect.NavigateToMenuScreen)
//            }
        }
    }

    fun loginWithGoogle() {
       //todo google login
    }
  }

//todo
internal sealed class UiEffect {
    data object NavigateToMainScreen: UiEffect()
    data class ShowSnackbar(@StringRes val resId: Int): UiEffect()
}