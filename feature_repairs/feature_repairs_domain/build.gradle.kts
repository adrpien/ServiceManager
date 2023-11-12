import RaamCostaNavigation.ksp

plugins {
    `android-library`
    `kotlin-android`
}

apply(from = "$rootDir/domain-module.gradle")

android {
    namespace = "com.example.feature_repairs_domain"
}
dependencies{
    implementation(project(Modules.core))
    implementation(project(Modules.featureAppDomain))
    testImplementation(project(Modules.test))
}