plugins {
    `android-library`
    `kotlin-android`
}

apply(from = "$rootDir/base-module.gradle")

android {
    namespace = "com.example.feature_repairs_data"
}
dependencies{
    implementation(project(Modules.core))
    implementation(project(Modules.featureRepairsDomain))

    // Firebase
    implementation(Firebase.firebaseAuth)
    implementation(Firebase.firebaseAuth)
    implementation(Firebase.firebaseFirestore)

    // Room
    "kapt"(Room.roomCompiler)
    implementation(Room.roomRuntime)
    implementation(Room.roomKtx)
}