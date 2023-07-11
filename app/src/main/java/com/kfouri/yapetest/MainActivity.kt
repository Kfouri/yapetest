package com.kfouri.yapetest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.kfouri.yapetest.ui.main.MainScreen
import com.kfouri.yapetest.ui.theme.YapeTestTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val screenSplash = installSplashScreen()
        super.onCreate(savedInstanceState)
        setContent {
            YapeTestTheme(darkTheme = false) {
                MainScreen()
            }
        }
        screenSplash.setKeepOnScreenCondition { false }
    }
}