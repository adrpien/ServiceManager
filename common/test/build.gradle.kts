plugins {
    `android-library`
    `kotlin-android`
}

apply(from = "$rootDir/base-module.gradle")

android {
    namespace = "com.example.test"
}
dependencies{
    implementation(project(Modules.core))
    implementation(project(Modules.featureAppDomain))
    implementation(project(Modules.featureInspectionsDomain))
    implementation(project(Modules.featureRepairsDomain))
}
