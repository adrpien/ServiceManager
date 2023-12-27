plugins {
    `android-library`
    `kotlin-android`
}

apply(from = "$rootDir/base-module.gradle")

android {
    namespace = "com.example.core"
}
dependencies{

    // Compose dependencies
    implementation(Compose.ui)
    implementation(Compose.uiToolingPreview)
    implementation(Compose.material)
    implementation(Compose.material3)
    implementation(Compose.activityCompose)
    implementation(Compose.viewModelCompose)
    implementation(Compose.hiltNavigationCompose)
    implementation(Compose.materialIconExtended)
    implementation(Compose.swipeRefresh)
    debugImplementation(Compose.uiTooling)
    implementation(AlertDialogs.vanpraMaterialDialog)
    implementation(AlertDialogs.vanpraMaterialDialogDateTime)

    // Glide
    implementation(Glide.glide)
    "kapt"(Glide.glideCompiler)

    // Date AlertDialog
    implementation(DateAlertDialog.dateTime)

    // Firebase
    implementation(Firebase.firebaseAuth)
    implementation(Firebase.firebaseStorage)
    implementation(Firebase.firebaseFirestore)

    // Gson
    implementation(Gson.gson)


    // Room
    "kapt"(Room.roomCompiler)
    implementation(Room.roomRuntime)
    implementation(Room.roomKtx)


    // Reflect
    implementation(Reflect.reflect)

    // implementation(project(Modules.logger))
    // implementation(project(Modules.sharedPreferences))
}






