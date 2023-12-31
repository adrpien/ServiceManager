
plugins {
    `android-library`
    `kotlin-android`
}

apply(from = "$rootDir/domain-module.gradle")

android {
    namespace = "com.example.feature_app_domain"
}
dependencies{
    implementation(project(Modules.core))
    implementation(project(Modules.sharedPreferences))
    implementation(project(Modules.logger))
}