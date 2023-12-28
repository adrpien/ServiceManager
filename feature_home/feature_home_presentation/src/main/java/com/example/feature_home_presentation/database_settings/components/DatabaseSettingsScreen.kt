package com.example.feature_home_presentation.database_settings.components

import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.net.Uri
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
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
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
import com.example.feature_home_presentation.import_inspections.components.ImportInspectionsAlertDialog
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

@Composable
fun DatabaseSettingsScreen(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    viewModel: DatabaseSettingsViewModel = hiltViewModel(),
){
    val importInspectionDialogState = rememberMaterialDialogState()

    val context = LocalContext.current
    val activity = (LocalContext.current as? Activity)

    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    val contentResolver: ContentResolver = context.contentResolver

    /* ************************** File Picker *************************************************** */
    val pickFileIntent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
        addCategory(Intent.CATEGORY_OPENABLE)
        // type = "application/vnd.ms-excel"
        type = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
    }
    val pickFileLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.data?.let { uri ->
                val file = getFileFromUri(uri, context)
                file?.let {
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
        // scaffoldState.snackbarHostState.showSnackbar("Wait for implementation")

    }

    val manageHospitalListMenuItemState = MenuItemState(
        icon = Icons.Default.LocalHospital,
        text = UiText.StringResource(R.string.manage_hospital_list)
    ) {
        coroutineScope.launch {
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

                is UiEvent.ShowImportInspectionsDialog -> {
                    // TODO Open alert dialog here
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
        ImportInspectionsAlertDialog(
            dialogState = importInspectionDialogState,
            onClick = {
                      viewModel.onEvent(DatabaseSettingsEvent.SaveInspections)
                      },
            content = stringResource(id = R.string.importing)
        )
    }

}

fun getFileFromUri(uri: Uri, context: Context): File? {
    var inputStream: InputStream? = null
    var outputStream: FileOutputStream? = null
    try {
        val contentResolver: ContentResolver = context.contentResolver
        inputStream = contentResolver.openInputStream(uri)

        val targetFile = File(context.getExternalFilesDir(null), "temp_file.txt")
        outputStream = FileOutputStream(targetFile)

        val buffer = ByteArray(1024)
        var bytesRead: Int
        while (inputStream.read(buffer).also { bytesRead = it } != -1) {
            outputStream.write(buffer, 0, bytesRead)
        }

        return targetFile
    } catch (e: IOException) {
        e.printStackTrace()
    } finally {
        try {
            inputStream?.close()
            outputStream?.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    return null

}
