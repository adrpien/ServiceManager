package com.example.servicemanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.servicemanager.navigation.LoginNavigation
import com.example.shared_preferences.AppPreferences
import com.example.servicemanager.theme.ServiceManagerTheme
import com.example.servicemanager.navigation.MainScreenNavigation
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity (
) : ComponentActivity() {

    @Inject
    lateinit var appPreferences: AppPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ServiceManagerTheme(appPreferences) {
                //LoginNavigation()

                // Only for testing
                MainScreenNavigation("itMTZFY1praZQW8Z7EZjfl0Zj8R2")
            }
        }
    }
}
