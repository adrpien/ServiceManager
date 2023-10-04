plugins {
    `android-library`
    `kotlin-android`
}

apply(from = "$rootDir/presentation-module.gradle")

android {
    namespace = "com.example.feature_app_presentation"
}
dependencies{
    implementation(project(Modules.core))
    implementation(project(Modules.featureAppDomain))
}