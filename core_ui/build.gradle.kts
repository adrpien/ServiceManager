plugins {
    `android-library`
    `kotlin-android`
}

apply(from = "$rootDir/presentation-module.gradle")

android {
    namespace = "com.example.core_ui"
}
dependencies{
    implementation(project(Modules.core))
}