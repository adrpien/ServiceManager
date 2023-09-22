plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("kotlin-parcelize")
    id("com.google.devtools.ksp") version "1.8.0-1.0.9"
}

android {
    namespace  = "com.example.servicemanager"
    compileSdk = 33

    defaultConfig {
        compileSdkPreview = "UpsideDownCake"
        applicationId  = "com.example.servicemanager"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "com.example.servicemanager.core.HiltTestRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        kapt {
            arguments {
                arg("room.schemaLocation", "$projectDir/schemas")
            }
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_7
        targetCompatibility = JavaVersion.VERSION_1_7
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.0"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(AndroidX.coreKtx)
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.3.1")
    implementation("androidx.activity:activity-compose:1.3.1")
    implementation("androidx.compose.ui:ui:1.4.2")
    implementation("androidx.compose.ui:ui-tooling-preview:1.4.2")
    implementation("androidx.compose.material:material:1.2.0")
    debugImplementation("androidx.compose.ui:ui-tooling:1.4.2")
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.4.2")

    // Firebase
    implementation(Firebase.firebaseAuth)
    implementation(Firebase.firebaseAuth)
    implementation(Firebase.firebaseFirestore)

    // Glide
    implementation(Glide.glide)
    kapt(Glide.glideCompiler)

    // Compose dependencies
    implementation(Compose.viewModelCompose)
    implementation(Compose.activityCompose)
    implementation(Compose.material3)
    implementation(Compose.hiltNavigationCompose)
    implementation(Compose.materialIconExtended)
    implementation(Compose.swipeRefresh)

    // Compose Nav Destinations
    implementation (RaamCostaNavigation.composeDestinationsCore)
    ksp(RaamCostaNavigation.ksp)

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.6.4")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.5.1")

    //Dagger - Hilt
    kapt(DaggerHilt.kaptHiltAndroidCompiler)
    implementation(DaggerHilt.hiltAndroid)
    kapt(DaggerHilt.kaptHiltCompiler)
    implementation(DaggerHilt.hiltNavigationCompose)

    // Room
    kapt(Room.roomCompiler)
    implementation(Room.roomRuntime)
    implementation(Room.roomKtx)

    // Date AlertDialog
    implementation(DateAlertDialog.dateTime)

    // Local unit tests
    testImplementation(Testing.coreKtxTesting)
    testImplementation(Testing.junit4)
    testImplementation(Testing.coreTesting)
    testImplementation(Testing.truth)
    testImplementation(Testing.coroutines)
    testImplementation(Testing.mockWebServer)
    testImplementation(Testing.mockk)
    debugImplementation(Testing.testManifest)

    // Instrumentation tests
    androidTestImplementation(Testing.coreTesting)
    androidTestImplementation(Testing.coreKtxTesting)
    androidTestImplementation(Testing.testRunner)
    androidTestImplementation(Testing.junit4)
    androidTestImplementation(Testing.junitAndroidExt)
    androidTestImplementation(Testing.truth)
    androidTestImplementation(Testing.hiltTesting)
    androidTestImplementation(Testing.coroutines)
    androidTestImplementation(Testing.mockWebServer)
    androidTestImplementation(Testing.mockk)
    androidTestImplementation(Testing.mockkAndroid)
    androidTestImplementation(Testing.composeUiTest)
    androidTestImplementation(Testing.espresso)
    kaptAndroidTest(Testing.hiltTesting)
}