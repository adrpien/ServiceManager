import RaamCostaNavigation.ksp

plugins {
    `android-library`
    `kotlin-android`
}

apply(from = "$rootDir/base-module.gradle")

android {
    namespace = "com.example.feature_authentication_domain"
}
dependencies{

    implementation(project(Modules.core))
}