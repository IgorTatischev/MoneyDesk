package com.money.desk.authorization.presentation.screens.forgot_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.money.authorization.impl.R
import com.money.ui.components.BaseTextField
import com.money.ui.components.MainButton
import kotlinx.coroutines.flow.collectLatest
import com.money.ui.theme.MoneyDeskTheme
import com.money.ui.theme.Spacing
import com.money.ui.util.ScreenPreviews

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ForgotPasswordScreen(
    navigateBack: () -> Unit,
    viewModel: ForgotPasswordViewModel = hiltViewModel(),
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = true) {
        viewModel.uiEffect.collectLatest { effect ->
            when (effect) {
                is UiEffect.NavigateToSignInScreen -> navigateBack()

                is UiEffect.ShowSnackbar -> {
                    snackBarHostState.showSnackbar(effect.message.toString())
                }
            }
        }
    }

    ForgotPasswordContent(
        snackBarHostState = snackBarHostState,
        state = state,
        navigateBack = navigateBack,
        changeEmail = viewModel::changeEmailText,
        resetPassword = viewModel::resetPassword,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ForgotPasswordContent(
    snackBarHostState: SnackbarHostState,
    state: ForgotState,
    navigateBack: () -> Unit,
    changeEmail: (String) -> Unit,
    resetPassword: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { }, navigationIcon = {
                IconButton(onClick = navigateBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null
                    )
                }
            })
        },
        snackbarHost = { SnackbarHost(snackBarHostState) }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = Spacing.xxl),
            verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.forgot_title),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(20.dp))
            BaseTextField(
                value = state.emailText,
                label = R.string.login,
                icon = Icons.Outlined.AccountCircle,
                onValueChange = changeEmail
            )

            Spacer(modifier = Modifier.height(20.dp))

            MainButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = resetPassword,
                loading = state.isLoading,
                text = stringResource(id = R.string.send)
            )
        }
    }
}

@ScreenPreviews
@Composable
private fun ForgotPasswordContentPreview() {
    MoneyDeskTheme {
        ForgotPasswordContent(
            snackBarHostState = remember { SnackbarHostState() },
            state = ForgotState(emailText = "user@mail.com"),
            navigateBack = {},
            changeEmail = {},
            resetPassword = {},
        )
    }
}
