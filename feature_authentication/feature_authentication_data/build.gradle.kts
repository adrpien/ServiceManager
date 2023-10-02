plugins {
    `android-library`
    `kotlin-android`
    id("dagger.hilt.android.plugin")

}

apply(from = "$rootDir/data-module.gradle")

android {
    namespace = "com.example.feature_authentication_data"
}
dependencies{
    implementation(project(Modules.core))
    implementation(project(Modules.featureAppDomain))
    implementation(project(Modules.featureAuthenticationDomain))

}