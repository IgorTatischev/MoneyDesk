package com.money.desk.authorization.presentation.screens.sign_up

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Badge
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Password
import androidx.compose.material.icons.outlined.RecentActors
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.money.authorization.R
import com.money.desk.authorization.presentation.screens.sign_in.SignInViewModel
import com.money.ui.components.BaseTextField
import com.money.ui.components.MainButton
import kotlinx.coroutines.flow.collectLatest
import kotlinx.serialization.json.JsonNull.content

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

    LaunchedEffect(key1 = true) {
        viewModel.uiEffect.collectLatest { event ->
            when (event) {
                is UiEffect.NavigateToSignInScreen -> {
                    navigateToSignIn(event.login)
                }

                is UiEffect.ShowSnackbar -> {
                    snackBarHostState.showSnackbar(
                        if (event.resId != null)
                            context.getString(event.resId)
                        else event.message.toString()
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
                value = state.passwordText,
                label = R.string.password,
                icon = Icons.Outlined.Password,
                onValueChange = viewModel::changePasswordText
            )
            BaseTextField(
                value = state.nameText,
                label = R.string.name,
                icon = Icons.Outlined.Edit,
                onValueChange = viewModel::changeNameText
            )

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