package com.oliinyk.yaroslav.easyreads

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.oliinyk.yaroslav.easyreads.ui.navigation.AppNavHost
import com.oliinyk.yaroslav.easyreads.ui.theme.EasyReadsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EasyReadsTheme {
                val navHostController = rememberNavController()
                AppNavHost(navHostController)
            }
        }
    }
}
