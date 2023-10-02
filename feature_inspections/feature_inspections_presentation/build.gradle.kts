plugins {
    `android-library`
    `kotlin-android`
    id("com.google.devtools.ksp") version "1.8.10-1.0.9"
    id("dagger.hilt.android.plugin")

}

apply(from = "$rootDir/presentation-module.gradle")

android {
    namespace = "com.example.feature_inspections_presentation"
}
dependencies{
    implementation(project(Modules.core))
    implementation(project(Modules.featureAppDomain))
    implementation(project(Modules.featureInspectionsDomain))
    implementation(project(Modules.featureAppPresentation))
}