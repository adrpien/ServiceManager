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
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.core_ui.components.other.SwitchItem
import com.example.feature_home_presentation.R
import com.example.feature_home_presentation.database_settings.AppSettingsScreenEvent
import com.example.feature_home_presentation.database_settings.AppSettingsScreenViewModel

@Composable
fun AppSettingsScreen(
    navHostController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: AppSettingsScreenViewModel = hiltViewModel(),
) {

    val appSettingsState = viewModel.appSettingsScreenState

    val coroutineScope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    val scrollState = rememberScrollState()

    Scaffold(
        scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.secondary)
                .padding(it)
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
            SwitchItem(
                title = R.string.dark_mode,
                isChecked = appSettingsState.value.isDarkModeEnabled,
                isEnabled = true,
            ){
                viewModel.onEvent(AppSettingsScreenEvent.SetDarkMode(it))
            }

        }
    }

}