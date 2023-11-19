plugins {
    `android-library`
    `kotlin-android`
}

apply(from = "$rootDir/data-module.gradle")

android {
    namespace = "com.example.feature_repairs_data"
}
dependencies{
    implementation(project(Modules.core))
    implementation(project(Modules.featureAppDomain))
    implementation(project(Modules.featureRepairsDomain))
    implementation(project(Modules.loggerDomain))
    implementation(project(Modules.loggerData))

}