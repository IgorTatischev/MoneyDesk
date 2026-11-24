package com.money.desk.authorization.presentation.screens.sign_up

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Password
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalResources
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.money.authorization.impl.R
import com.money.common.PasswordValidationState
import com.money.ui.theme.extendedColors
import com.money.ui.components.BaseTextField
import com.money.ui.components.MainButton
import kotlinx.coroutines.flow.collectLatest
import com.money.ui.theme.MoneyDeskTheme
import com.money.ui.theme.Spacing
import com.money.ui.util.ScreenPreviews

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SignUpScreen(
    navigateBack: () -> Unit,
    navigateToSignIn: (String) -> Unit,
    viewModel: SignUpViewModel = hiltViewModel(),
) {

    val resources by rememberUpdatedState(LocalResources.current)
    val snackBarHostState = remember { SnackbarHostState() }
    val state by viewModel.signUpState.collectAsStateWithLifecycle()
    val passwordError by viewModel.passwordError.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = true) {
        viewModel.uiEffect.collectLatest { effect ->
            when (effect) {
                is UiEffect.NavigateToSignInScreen -> {
                    navigateToSignIn(effect.login)
                }

                is UiEffect.ShowSnackbar -> {
                    snackBarHostState.showSnackbar(
                        if (effect.messageRes != null)
                            resources.getString(effect.messageRes)
                        else effect.message.toString()
                    )
                }
            }
        }
    }

    SignUpContent(
        snackBarHostState = snackBarHostState,
        state = state,
        passwordError = passwordError,
        navigateBack = navigateBack,
        changeLogin = viewModel::changeLoginText,
        changeName = viewModel::changeNameText,
        changePassword = viewModel::changePasswordText,
        signUp = viewModel::signUp,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SignUpContent(
    snackBarHostState: SnackbarHostState,
    state: SignUpState,
    passwordError: PasswordValidationState,
    navigateBack: () -> Unit,
    changeLogin: (String) -> Unit,
    changeName: (String) -> Unit,
    changePassword: (String) -> Unit,
    signUp: () -> Unit,
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

            BaseTextField(
                value = state.loginText,
                label = R.string.login,
                icon = Icons.Outlined.AccountCircle,
                onValueChange = changeLogin
            )
            BaseTextField(
                value = state.nameText,
                label = R.string.name,
                icon = Icons.Outlined.Edit,
                onValueChange = changeName
            )
            BaseTextField(
                value = state.passwordText,
                label = R.string.password,
                icon = Icons.Outlined.Password,
                onValueChange = changePassword,
                isError = state.passwordText.isNotEmpty() && !passwordError.successful
            )

            Spacer(modifier = Modifier.height(Spacing.xs))

            Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
                ConditionRow(
                    condition = stringResource(id = R.string.minimum_condition),
                    check = passwordError.hasMinimum
                )
                ConditionRow(
                    condition = stringResource(id = R.string.capitalized_condition),
                    check = passwordError.hasCapitalizedLetter
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            MainButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = signUp,
                loading = state.isLoading,
                text = stringResource(id = R.string.sign_up)
            )
        }
    }
}

@Composable
fun ConditionRow(
    condition: String,
    check: Boolean,
) {
    val color by animateColorAsState(
        targetValue = if (check) {
            MaterialTheme.extendedColors.validationSuccess
        } else {
            MaterialTheme.extendedColors.validationError
        },
        label = "text color"
    )

    val icon = if (check) {
        Icons.Rounded.Check
    } else {
        Icons.Rounded.Close
    }

    Row {
        Icon(
            imageVector = icon,
            tint = color,
            contentDescription = "status icon"
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = condition,
            color = color
        )
    }
}

@ScreenPreviews
@Composable
private fun SignUpContentPreview() {
    MoneyDeskTheme {
        SignUpContent(
            snackBarHostState = remember { SnackbarHostState() },
            state = SignUpState(loginText = "user@mail.com", nameText = "Igor", passwordText = "Passw0rd"),
            passwordError = PasswordValidationState(hasMinimum = true, hasCapitalizedLetter = false),
            navigateBack = {},
            changeLogin = {},
            changeName = {},
            changePassword = {},
            signUp = {},
        )
    }
}
