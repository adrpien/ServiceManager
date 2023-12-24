plugins {
    `android-library`
    `kotlin-android`
}

apply(from = "$rootDir/data-module.gradle")

android {
    namespace = "com.example.feature_app_data"
}
dependencies{
    implementation(project(Modules.core))
    implementation(project(Modules.sharedPreferences))
    implementation(project(Modules.logger))
    implementation(project(Modules.featureAppDomain))
}