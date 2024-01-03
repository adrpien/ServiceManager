package com.example.servicemanager

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import com.example.servicemanager.navigation.MainScreenNavigation
import com.example.servicemanager.network_connection.RequestNetworkObserver
import com.example.servicemanager.theme.ServiceManagerTheme
import com.example.shared_preferences.AppPreferences
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


// TODO Clean up ap dependencies
// TODO Add app documentation, readme, test data
// TODO Finish app testing

@AndroidEntryPoint
class MainActivity (
) : ComponentActivity() {

    @Inject
    lateinit var appPreferences: AppPreferences
    @Inject
    lateinit var requestNetworkObserver: RequestNetworkObserver

    lateinit var permissionResultListener: ActivityResultLauncher<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        permissionResultListener = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { result ->
            when(result) {
                true -> Unit
                false -> ActivityCompat.startActivityForResult(this, intent, 0, null)
                else -> ActivityCompat.startActivityForResult(this, intent, 0, null)
            }
        }

        permissionResultListener.launch(Settings.ACTION_MANAGE_WRITE_SETTINGS)

        if (!Settings.System.canWrite(this)) {
            val intent = Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS)
            intent.data = Uri.parse("package:" + this.packageName)
            ActivityCompat.startActivityForResult(this, intent, 0, null)
        }

        requestNetworkObserver(
            activity = this,
            onLost = null,
            onAvailable = null
        )

        setContent {
            ServiceManagerTheme(appPreferences) {
                //LoginNavigation()

                // Only for testing
                MainScreenNavigation("itMTZFY1praZQW8Z7EZjfl0Zj8R2")
            }

        }
    }



}
