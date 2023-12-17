package com.example.feature_home_presentation.home.components

import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DataArray
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.core.util.Helper
import com.example.core.util.Screen
import com.example.core.util.UiText
import com.example.core_ui.components.menu.MenuItemState
import com.example.core_ui.components.snackbar.AppSnackbar
import com.example.feature_home_presentation.R
import com.example.feature_home_presentation.home.HomeEvent
import com.example.feature_home_presentation.home.HomeViewModel
import com.example.feature_home_presentation.home.UiEvent
import com.example.servicemanager.feature_home_domain.model.Profile
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    userId: String?,
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel(),
) {


    val showAboutDialog = remember {
        mutableStateOf(false)
    }

    val context = LocalContext.current
    val activity = (LocalContext.current as? Activity)
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    val logOutMenuItemState = MenuItemState(
        icon = Icons.Default.Logout,
        text = UiText.StringResource(R.string.log_out)
    ) {
        viewModel.onEvent(HomeEvent.LogOut)
    }

    val databaseSettingMenuItemState = MenuItemState(
        icon = Icons.Default.DataArray,
        text = UiText.StringResource(R.string.database_settings)
    ) {
        coroutineScope.launch {
            scaffoldState.snackbarHostState.showSnackbar("Wait for implementation")
        }
    }

    val appSettingMenuItemState = MenuItemState(
        icon = Icons.Default.Settings,
        text = UiText.StringResource(R.string.settings)
    ) {
        navHostController.navigate(Screen.AppSettingsScreen.route)
    }

    val aboutMenuItemState = MenuItemState(
        icon = Icons.Default.Info,
        text = UiText.StringResource(R.string.about)
    ) {
        showAboutDialog.value = true
    }

    val menuItems = listOf(
        databaseSettingMenuItemState,
        appSettingMenuItemState,
        aboutMenuItemState,
        logOutMenuItemState,
    )
    
    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collect() { event ->
            when(event) {
                is UiEvent.Navigate -> {
                    navHostController.navigate(event.screen.route)
                }
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message.asString(context)
                    )
                }
                UiEvent.FinishApp -> {
                    activity?.finish()
                }
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        snackbarHost = {
            SnackbarHost(hostState = scaffoldState.snackbarHostState) {
                AppSnackbar(
                    data = it,
                    // can be mutableState here, but for me like this is ok
                    onActionClick = {
                        it.dismiss()
                    }
                )
            }
        },
        backgroundColor = MaterialTheme.colorScheme.secondary
    ) { padding ->

        Column(
            modifier = Modifier.padding(padding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            val profilePicture = Helper.drawableToByteArray(context.getDrawable(R.drawable.default_profile_picture)!!
            )

            ProfileSection(
                profile = Profile(
                    profilePicture = profilePicture,
                    pointsThisMonth = 15,
                    user = viewModel.homeState.value.user
                )
            )
            LazyColumn() {
                items(menuItems) { item ->
                    MenuItem(menuItemState = item)
                }
            }
        }

        if(showAboutDialog.value) {
            AboutAlertDialog(
                title = UiText.StringResource(R.string.about),
                context = context,
                onConfirm = { showAboutDialog.value = false },
                onDismissRequest = { showAboutDialog.value = false }
            )
        }
    }
}