package com.money.desk

import android.app.Activity
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalContext
import com.google.firebase.FirebaseApp
import com.money.desk.navigation.RootHost
import com.money.ui.theme.MoneyDeskTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        //todo splash
        FirebaseApp.initializeApp(applicationContext)

        setContent {
            val context = LocalContext.current

            MoneyDeskTheme {
                RootHost()
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
