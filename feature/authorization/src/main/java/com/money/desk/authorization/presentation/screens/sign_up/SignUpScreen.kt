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
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.money.authorization.R
import com.money.ui.components.BaseTextField
import com.money.ui.components.MainButton
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SignUpScreen(
    navigateBack: () -> Unit,
    navigateToSignIn: (String) -> Unit,
    viewModel: SignUpViewModel = hiltViewModel(),
) {

    val context = LocalContext.current
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
                        if (effect.resId != null)
                            context.getString(effect.resId)
                        else effect.message.toString()
                    )
                }
            }
        }
    }

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
                .padding(horizontal = 40.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            BaseTextField(
                value = state.loginText,
                label = R.string.login,
                icon = Icons.Outlined.AccountCircle,
                onValueChange = viewModel::changeLoginText
            )
            BaseTextField(
                value = state.nameText,
                label = R.string.name,
                icon = Icons.Outlined.Edit,
                onValueChange = viewModel::changeNameText
            )
            BaseTextField(
                value = state.passwordText,
                label = R.string.password,
                icon = Icons.Outlined.Password,
                onValueChange = viewModel::changePasswordText,
                isError = passwordError.successful
            )

            Spacer(modifier = Modifier.height(8.dp))

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
                onClick = viewModel::signUp,
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
        targetValue = if (check) Color.Green else Color.Red,
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