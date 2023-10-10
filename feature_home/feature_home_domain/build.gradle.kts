import RaamCostaNavigation.ksp

plugins {
    `android-library`
    `kotlin-android`
}

apply(from = "$rootDir/domain-module.gradle")

android {
    namespace = "com.example.feature_home_domain"
}
dependencies{
    implementation(project(Modules.core))
    implementation(project(Modules.featureAppDomain))
}