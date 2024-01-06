plugins {
    `android-library`
    `kotlin-android`
}

apply(from = "$rootDir/data-module.gradle")

android {
    namespace = "com.example.caching_data"
}
dependencies{
    // Modules
    implementation(project(Modules.core))
    implementation(project(Modules.featureInspectionsDomain))
    implementation(project(Modules.featureInspectionsData))
    implementation(project(Modules.featureRepairsDomain))
    implementation(project(Modules.featureRepairsData))
    implementation(project(Modules.cachingDomain))
}