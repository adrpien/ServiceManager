plugins {
    `android-library`
    `kotlin-android`
}

apply(from = "$rootDir/presentation-module.gradle")

android {
    namespace = "com.example.feature_repairs_presentation"
}
dependencies{
    implementation(project(Modules.core))
    implementation(project(Modules.coreUi))
    implementation(project(Modules.sharedPreferences))
    implementation(project(Modules.logger))
    implementation(project(Modules.commonDomain))
    implementation(project(Modules.featureRepairsDomain))
}