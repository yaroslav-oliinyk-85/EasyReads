package com.oliinyk.yaroslav.easyreads

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.platform.ComposeView
import androidx.navigation.compose.rememberNavController
import com.oliinyk.yaroslav.easyreads.ui.navigation.AppNavHost
import com.oliinyk.yaroslav.easyreads.ui.theme.EasyReadsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        setContent{
            EasyReadsTheme {
                val navHostController = rememberNavController()
                AppNavHost(navHostController)
            }
        }
    }
}