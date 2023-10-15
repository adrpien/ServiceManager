plugins {
    `android-library`
    `kotlin-android`
}

apply(from = "$rootDir/presentation-module.gradle")

android {
    namespace = "com.example.feature_inspections_presentation"
}
dependencies{
    implementation(project(Modules.core))
    implementation(project(Modules.coreUi))
    implementation(project(Modules.featureAppDomain))
    implementation(project(Modules.featureInspectionsDomain))
}