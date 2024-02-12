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
    implementation("com.google.android.material:material:1.11.0")
    implementation(project(Modules.commonDomain))
}