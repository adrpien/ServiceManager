plugins {
    `android-library`
    `kotlin-android`
    id("dagger.hilt.android.plugin")

}

apply(from = "$rootDir/base-module.gradle")

android {
    namespace = "com.example.feature_authentication_data"
}
dependencies{
    implementation(project(Modules.core))
    implementation(project(Modules.featureAppDomain))
    implementation(project(Modules.featureAuthenticationDomain))

    // Firebase
    implementation(Firebase.firebaseAuth)
    implementation(Firebase.firebaseAuth)
    implementation(Firebase.firebaseFirestore)

    // Room
    "kapt"(Room.roomCompiler)
    implementation(Room.roomRuntime)
    implementation(Room.roomKtx)
}