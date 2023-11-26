package com.example.feature_home_presentation.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DataArray
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.graphics.drawable.toBitmap
import androidx.navigation.NavHostController
import com.example.core.util.Helper
import com.example.core.util.Screens
import com.example.core.util.UiText
import com.example.feature_home_presentation.R
import com.example.servicemanager.feature_app_domain.model.User
import com.example.servicemanager.feature_home_domain.model.Profile
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
) {

    val showAboutDialog = remember {
        mutableStateOf(false)
    }

    val context = LocalContext.current
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    val logOutMenuItemState = MenuItemState(
        icon = Icons.Default.Logout,
        text = UiText.StringResource(R.string.log_out)
    ) {
        coroutineScope.launch {
            scaffoldState.snackbarHostState.showSnackbar("Log out clicked!")
        }
    }

    val databaseSettingMenuItemState = MenuItemState(
        icon = Icons.Default.DataArray,
        text = UiText.StringResource(R.string.database_settings)
    ) {
        navHostController.navigate(Screens.DatabaseSettingsScreen.route)
    }

    val appSettingMenuItemState = MenuItemState(
        icon = Icons.Default.Settings,
        text = UiText.StringResource(R.string.settings)
    ) {
        navHostController.navigate(Screens.AppSettingsScreen.route)
    }

    val aboutMenuItemState = MenuItemState(
        icon = Icons.Default.Info,
        text = UiText.StringResource(R.string.about)
    ) {
        coroutineScope.launch {
            scaffoldState.snackbarHostState.showSnackbar("App settings clicked!")
        }
    }

    val menuItems = listOf<MenuItemState>(
        databaseSettingMenuItemState,
        appSettingMenuItemState,
        aboutMenuItemState,
        logOutMenuItemState,
    )

    Scaffold(
        scaffoldState = scaffoldState
    ) { padding ->

        Column(
            modifier = Modifier.padding(padding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            val profilePicture = Helper.drawableToByteArray(context,
                context.getDrawable(R.drawable.default_profile_picture)!!
            )

            ProfileSection(
                profile = Profile(
                    profilePicture = profilePicture,
                    pointsThisMonth = 15,
                    user = User()
                )
            )
            LazyColumn() {
                items(menuItems) { item ->
                    MenuItem(menuItemState = item)
                }
            }


        }
    }
}