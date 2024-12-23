package com.money.desk

import android.app.Activity
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.money.desk.navigation.RootHost
import com.money.ui.theme.MoneyDeskTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var auth: FirebaseAuth
    private val credentialManager = CredentialManager.create(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        //todo splash
        FirebaseApp.initializeApp(applicationContext)

        setContent {
            val viewModel: MainViewModel = viewModel()
            val context = LocalContext.current
            val scope = rememberCoroutineScope()

            MoneyDeskTheme {
                RootHost(
                    isLogin = auth.currentUser != null,
                    onboardingCheck = viewModel.onboardingCheck.value,
                    onSignOut = {
                        scope.launch {
                            try {
                                credentialManager.clearCredentialState(ClearCredentialStateRequest())
                            } catch (e: Exception) {
                                Log.e("credential", e.message.toString())
                            }
                        }
                        auth.signOut()
                    }
                )
            }

            DisposableEffect(key1 = Unit) {
                val activity = context as Activity
                val originalOrientation = activity.requestedOrientation
                activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                onDispose {
                    activity.requestedOrientation = originalOrientation
                }
            }
        }
    }
}
