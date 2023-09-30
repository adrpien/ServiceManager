plugins {
    `android-library`
    `kotlin-android`
}

apply(from = "$rootDir/base-module.gradle")

android {
    namespace = "com.example.core"
}

dependencies{

    // Firebase
    implementation(Firebase.firebaseAuth)
    implementation(Firebase.firebaseStorage)
    implementation(Firebase.firebaseFirestore)

    // Room
    "kapt"(Room.roomCompiler)
    implementation(Room.roomRuntime)
    implementation(Room.roomKtx)
}




