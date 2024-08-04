package com.money.desk.authorization.presentation.screens.sign_in

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.money.authorization.R
import kotlinx.coroutines.flow.collectLatest

@Composable
internal fun SignInScreen(
    navigateToMain: () -> Unit,
    navigateToSignUp: () -> Unit,
    navigateForgot: () -> Unit,
    loginFromRegister: String = "",
    viewModel: SignInViewModel = hiltViewModel(),
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val state = viewModel.signInState.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.uiEffect.collectLatest { event ->
            when (event) {
                is UiEffect.NavigateToMainScreen -> navigateToMain()

                is UiEffect.ShowSnackbar -> {
                    snackBarHostState.showSnackbar(event.message.toString())
                }
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState) }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            if (!state.value.isLoading) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.Center)
                        .padding(horizontal = 40.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val (login, setLogin) = rememberSaveable { mutableStateOf(loginFromRegister) }
                    val (password, setPass) = rememberSaveable { mutableStateOf("") }
                    val isPasswordVisible = rememberSaveable {
                        mutableStateOf(false)
                    }

                    OutlinedTextField(
                        value = login,
                        onValueChange = setLogin,
                        singleLine = true,
                        shape = RoundedCornerShape(15.dp),
                        label = { Text(text = stringResource(id = R.string.login)) },
                        placeholder = { Text(text = stringResource(id = R.string.login_hint)) },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Outlined.AccountCircle,
                                contentDescription = null
                            )
                        },
                    )

                    OutlinedTextField(
                        value = password,
                        onValueChange = setPass,
                        singleLine = true,
                        shape = RoundedCornerShape(30.dp),
                        label = { Text(text = stringResource(id = R.string.password)) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        visualTransformation =
                        if (!isPasswordVisible.value) PasswordVisualTransformation() else VisualTransformation.None,
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Outlined.Lock,
                                contentDescription = null
                            )
                        },
                        trailingIcon = {
                            IconButton(
                                onClick = { isPasswordVisible.value = !isPasswordVisible.value }
                            ) {
                                Icon(
                                    imageVector = if (!isPasswordVisible.value) Icons.Outlined.VisibilityOff else Icons.Outlined.Visibility,
                                    contentDescription = null,
                                )
                            }
                        },
                    )

                    Spacer(modifier = Modifier.height(40.dp))

                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { viewModel.signIn(login, password) }
                    ) {
                        Text(text = stringResource(id = R.string.sign_in))
                    }

                    ElevatedButton(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = navigateToSignUp
                    ) {
                        Text(text = stringResource(id = R.string.sign_up))
                    }

                    Spacer(modifier = Modifier.height(30.dp))

                    FilledIconButton(
                        modifier = Modifier.height(IntrinsicSize.Max).width(IntrinsicSize.Max),
                        onClick = { viewModel.loginWithGoogle() },
                        colors = IconButtonDefaults.outlinedIconButtonColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant,
                            contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    ) {
                        Icon(
                            modifier = Modifier.size(30.dp),
                            painter = painterResource(id = com.money.ui.R.drawable.google_icon),
                            contentDescription = null
                        )
                    }
                }

                TextButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .padding(20.dp),
                    onClick = navigateForgot
                ) {
                    Text(text = stringResource(id = R.string.forgot))
                }
            } else
                CircularProgressIndicator(Modifier.align(Alignment.Center))
        }
    }
}