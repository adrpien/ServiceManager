package com.example.servicemanager.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.servicemanager.navigation.ContentNavigation
import com.example.servicemanager.navigation.LoginNavigation
import com.example.servicemanager.ui.components.ContentComposable
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ContentComposable()
            //val navHostController = rememberNavController()
            //LoginNavigation(navHostController)
        }
    }
}
