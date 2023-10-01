plugins {
    `android-library`
    `kotlin-android`
    id("dagger.hilt.android.plugin")

}

apply(from = "$rootDir/base-module.gradle")

android {
    namespace = "com.example.feature_inspections_data"
}
dependencies{
    implementation(project(Modules.core))
    implementation(project(Modules.featureAppDomain))
    implementation(project(Modules.featureInspectionsDomain))

    // Firebase
    implementation(Firebase.firebaseAuth)
    implementation(Firebase.firebaseStorage)
    implementation(Firebase.firebaseFirestore)

    // Room
    "kapt"(Room.roomCompiler)
    implementation(Room.roomRuntime)
    implementation(Room.roomKtx)
}