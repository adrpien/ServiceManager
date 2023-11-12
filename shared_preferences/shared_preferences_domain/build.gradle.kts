
plugins {
    `android-library`
    `kotlin-android`
}

apply(from = "$rootDir/domain-module.gradle")

android {
    namespace = "com.example.shared_preferences_domain"
}
dependencies{
    implementation(project(Modules.core))
}