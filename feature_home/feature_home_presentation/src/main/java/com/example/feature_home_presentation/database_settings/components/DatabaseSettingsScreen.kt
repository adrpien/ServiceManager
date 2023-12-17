package com.example.feature_home_presentation.database_settings.components

import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddChart
import androidx.compose.material.icons.filled.AddToPhotos
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.example.feature_home_presentation.database_settings.DatabaseSettingsViewModel
import com.example.feature_home_presentation.database_settings.UiEvent
import com.example.feature_home_presentation.home.components.MenuItem
import kotlinx.coroutines.launch

@Composable
fun DatabaseSettingsScreen(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    viewModel: DatabaseSettingsViewModel = hiltViewModel(),
){

    val context = LocalContext.current
    val activity = (LocalContext.current as? Activity)

    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    val addInspectionListMenuItemState = MenuItemState(
        icon = Icons.Default.AddToPhotos,
        text = UiText.StringResource(R.string.add_inspections_from_file)
    ) {
        coroutineScope.launch {
            scaffoldState.snackbarHostState.showSnackbar("Wait for implementation")
        }
    }

    val manageHospitalListMenuItemState = MenuItemState(
        icon = Icons.Default.LocalHospital,
        text = UiText.StringResource(R.string.manage_hospital_list)
    ) {
        coroutineScope.launch {
            //scaffoldState.snackbarHostState.showSnackbar("Wait for implementation")
            navHostController.navigate(Screen.HospitalListManagerScreen.route)
        }
    }


    val menuItems = listOf(
        addInspectionListMenuItemState,
        manageHospitalListMenuItemState
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
            LazyColumn() {
                items(menuItems) { item ->
                    MenuItem(menuItemState = item)
                }
            }
        }
    }
}