plugins {
    `android-library`
    `kotlin-android`
}

apply(from = "$rootDir/domain-module.gradle")

android {
    namespace = "com.example.caching_domain"
}
dependencies{
    // Modules
    implementation(project(Modules.core))
    implementation(project(Modules.featureInspectionsDomain))
    implementation(project(Modules.featureRepairsDomain))

}