package com.money.desk

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.money.desk.navigation.RootHost
import com.money.ui.theme.MoneyDeskTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        //todo splash
        setContent {
            MoneyDeskTheme {
                RootHost()
            }
        }
    }
}
