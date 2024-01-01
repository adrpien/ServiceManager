package com.example.feature_home_presentation.database_settings.components

import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddToPhotos
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.MaterialTheme
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
import com.example.feature_home_presentation.database_settings.DatabaseSettingsEvent
import com.example.feature_home_presentation.database_settings.DatabaseSettingsViewModel
import com.example.feature_home_presentation.database_settings.UiEvent
import com.example.feature_home_presentation.home.components.MenuItem
import com.example.feature_home_presentation.import_inspections.components.DefaultInspectionsDialogState
import com.example.feature_home_presentation.import_inspections.components.DefaultInspectionsLoadingDialogState
import com.example.feature_home_presentation.import_inspections.components.ImportInspectionsLoadingMaterialDialog
import com.example.feature_home_presentation.import_inspections.components.ImportInspectionsMaterialDialog
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import kotlinx.coroutines.launch
import java.io.InputStream

// TODO Add MaterialDialog for showing saving inspections progress
@Composable
fun DatabaseSettingsScreen(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    viewModel: DatabaseSettingsViewModel = hiltViewModel(),
){
    val context = LocalContext.current
    val activity = (LocalContext.current as? Activity)

    val state = viewModel.databaseSettings

    val importInspectionsLoadingMaterialDialogState = rememberMaterialDialogState()
    val importInspectionsLoadingDialogState = remember {
        mutableStateOf(
            DefaultInspectionsLoadingDialogState(
                text = "",
                counter = null
            )
        )
    }

    val importInspectionsDialogState = remember {
        mutableStateOf(DefaultInspectionsDialogState(
            text = "",
            counter = null
        ))
    }
    val importInspectionMaterialDialogState = rememberMaterialDialogState()

    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    val contentResolver: ContentResolver = context.contentResolver

    /* ************************** File Picker *************************************************** */
    val pickFileIntent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
        addCategory(Intent.CATEGORY_OPENABLE)
        type = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
    }
    val pickFileLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.data?.let { uri ->
                val inputStream: InputStream? = contentResolver.openInputStream(uri)
                inputStream?.let {
                    importInspectionsLoadingMaterialDialogState.show()
                    viewModel.onEvent(DatabaseSettingsEvent.ImportInspections(it))
                }
            }
        }
    }

    /* ************************** Database setting menu items *********************************** */
    val addInspectionListMenuItemState = MenuItemState(
        icon = Icons.Default.AddToPhotos,
        text = UiText.StringResource(R.string.add_inspections_from_file)
    ) {
        pickFileLauncher.launch(pickFileIntent)
    }

    val manageHospitalListMenuItemState = MenuItemState(
        icon = Icons.Default.AdminPanelSettings,
        text = UiText.StringResource(R.string.manage_hospital_list)
    ) {
        coroutineScope.launch {
            navHostController.navigate(Screen.HospitalListManagerScreen.route)
        }
    }

    val manageRepairStateListMenuItemState = MenuItemState(
        icon = Icons.Default.AdminPanelSettings,
        text = UiText.StringResource(R.string.manage_repair_state_list)
    ) {
        coroutineScope.launch {
            navHostController.navigate(Screen.RepairStateListManagerScreen.route)
        }
    }
    val manageInspectionStateListMenuItemState = MenuItemState(
        icon = Icons.Default.AdminPanelSettings,
        text = UiText.StringResource(R.string.manage_inspection_state_list)
    ) {
        coroutineScope.launch {
            navHostController.navigate(Screen.InspectionStateListManagerScreen.route)
        }
    }

    val manageEstStateListMenuItemState = MenuItemState(
        icon = Icons.Default.AdminPanelSettings,
        text = UiText.StringResource(R.string.manage_est_state_list)
    ) {
        coroutineScope.launch {
            navHostController.navigate(Screen.EstStateListManagerScreen.route)
        }
    }

    val manageTechnicianListMenuItemState = MenuItemState(
        icon = Icons.Default.AdminPanelSettings,
        text = UiText.StringResource(R.string.manage_technician_list)
    ) {
        coroutineScope.launch {
            navHostController.navigate(Screen.TechnicianListManagerScreen.route)
        }
    }

    val menuItems = listOf(
        addInspectionListMenuItemState,
        manageHospitalListMenuItemState,
        manageTechnicianListMenuItemState,
        manageEstStateListMenuItemState,
        manageInspectionStateListMenuItemState,
        manageRepairStateListMenuItemState
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

                is UiEvent.ShowImportInspectionsDialog -> {
                    importInspectionsLoadingMaterialDialogState.hide()
                    importInspectionMaterialDialogState.show()
                }

                is UiEvent.UpdateImportInspectionsLoadingDialogState -> {
                    importInspectionsLoadingDialogState.value = importInspectionsLoadingDialogState.value.copy(
                        text = event.text.asString(context),
                        counter = event.counter,
                    )
                }

                is UiEvent.UpdateImportInspectionsDialogState -> {
                    importInspectionsDialogState.value = importInspectionsDialogState.value.copy(
                        text = event.text.asString(context),
                        counter = event.counter,
                    )
                }

                UiEvent.HideImportInspectionsDialogs -> {
                    importInspectionsLoadingMaterialDialogState.hide()
                    importInspectionMaterialDialogState.hide()
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
        ImportInspectionsMaterialDialog(
            dialogState = importInspectionMaterialDialogState,
            onClick = {
                      viewModel.onEvent(DatabaseSettingsEvent.SaveInspections)
                      },
            text = importInspectionsDialogState.value.text + ": " + importInspectionsDialogState.value.counter,
        )

        ImportInspectionsLoadingMaterialDialog(
            dialogState = importInspectionsLoadingMaterialDialogState,
            state = importInspectionsLoadingDialogState
        )
    }

}
