
plugins {
    `android-library`
    `kotlin-android`
    id("dagger.hilt.android.plugin")

}

apply(from = "$rootDir/domain-module.gradle")

android {
    namespace = "com.example.feature_app_domain"
}
dependencies{
    implementation(project(Modules.core))
}