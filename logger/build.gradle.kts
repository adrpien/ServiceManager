plugins {
    `android-library`
    `kotlin-android`
}

apply(from = "$rootDir/base-module.gradle")

android {
    namespace = "com.example.logger"
}
dependencies{
    // Firebase
    implementation(Firebase.firebaseAnalytics)
    implementation(Firebase.firebaseAnalytisPlayServiceMeasurement)

    // Reflect
    // implementation(Reflect.reflect)

    // Modules
    implementation(project(Modules.core))
}