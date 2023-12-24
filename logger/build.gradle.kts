plugins {
    `android-library`
    `kotlin-android`
}

apply(from = "$rootDir/base-module.gradle")

android {
    namespace = "com.example.shared_preferences"
}
dependencies{
    implementation(project(Modules.core))
}