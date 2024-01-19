package com.example.feature_home_presentation.app_settings.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.core_ui.components.other.DefaultSelectionSection
import com.example.core_ui.components.other.SwitchItem
import com.example.core_ui.components.snackbar.AppSnackbar
import com.example.feature_home_presentation.R
import com.example.feature_home_presentation.app_settings.AppSettingsScreenEvent
import com.example.feature_home_presentation.app_settings.AppSettingsScreenViewModel
import com.example.feature_home_presentation.app_settings.UiEvent

@Composable
fun AppSettingsScreen(
    viewModel: AppSettingsScreenViewModel = hiltViewModel(),
) {

    val context = LocalContext.current

    val appSettingsState = viewModel.appSettingsScreenState.collectAsState()

    val coroutineScope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    val scrollState = rememberScrollState()



    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collect { event ->
            when(event) {
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(message = event.message.asString(context))
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
    ) { padding ->
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.secondary)
                .padding(padding)
                .padding(8.dp)
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            Text(
                text = stringResource(id = R.string.settings),
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onSecondary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Divider(
                color = MaterialTheme.colorScheme.onSecondary,
                modifier = Modifier.height(2.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                modifier = Modifier,
                text = "Dark mode:",
                color = MaterialTheme.colorScheme.onSecondary
            )
            SwitchItem(
                title = R.string.dark_mode,
                isChecked = appSettingsState.value.isDarkModeEnabled,
                isEnabled = true,
            ){
                viewModel.onEvent(AppSettingsScreenEvent.SetDarkMode(it))
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                modifier = Modifier,
                text = "Date formatting type:",
                color = MaterialTheme.colorScheme.onSecondary
            )
            DefaultSelectionSection(
                itemList = viewModel.appSettingsScreenState.value.dateFormattingTypeList,
                nameList = viewModel.appSettingsScreenState.value.dateFormattingTypeList.map { it.formatting },
                selectedItem = viewModel.appSettingsScreenState.value.dateFormattingType,
                onItemChanged = {
                                viewModel.onEvent(AppSettingsScreenEvent.SetDateFormattingType(it))
                },
                enabled = true
            )

        }
    }

}