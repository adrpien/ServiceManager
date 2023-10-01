plugins {
    `android-library`
    `kotlin-android`
    id("com.google.devtools.ksp") version "1.8.10-1.0.9"
    id("dagger.hilt.android.plugin")

}

apply(from = "$rootDir/compose-module.gradle")

android {
    namespace = "com.example.feature_repairs_presentation"
}
dependencies{

    implementation(project(Modules.core))
    implementation(project(Modules.featureAppDomain))
    implementation(project(Modules.featureRepairsDomain))


    // Glide
    implementation(Glide.glide)
    "kapt"(Glide.glideCompiler)

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

    // Compose Nav Destinations
    implementation (RaamCostaNavigation.composeDestinationsCore)
    ksp(RaamCostaNavigation.ksp)

    // Date AlertDialog
    implementation(DateAlertDialog.dateTime)
}