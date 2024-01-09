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
    implementation(project(Modules.featureAppDomain))
    implementation(project(Modules.cachingDomain))
    implementation(project(":feature_app:feature_app_data"))
}