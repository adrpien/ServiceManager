
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
    compileSdk = ProjectConfig.compileSdk

    defaultConfig {
        compileSdkPreview = ProjectConfig.compileSdkPreview
        applicationId  = ProjectConfig.appId
        minSdk = ProjectConfig.minSdk
        targetSdk = ProjectConfig.targetSdk
        versionCode = ProjectConfig.versionCode
        versionName = ProjectConfig.versionName

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
        kotlinCompilerExtensionVersion = Compose.composeCompilerVersion
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    // Modules
    implementation(project(Modules.core))
    implementation(project(Modules.coreUi))
    implementation(project(Modules.featureAppData))
    implementation(project(Modules.featureAppDomain))
    implementation(project(Modules.featureAuthenticationDomain))
    implementation(project(Modules.featureAuthenticationData))
    implementation(project(Modules.featureAuthenticationPresentation))
    implementation(project(Modules.featureInspectionsData))
    implementation(project(Modules.featureInspectionsDomain))
    implementation(project(Modules.featureInspectionsPresentation))
    implementation(project(Modules.featureRepairsData))
    implementation(project(Modules.featureRepairsDomain))
    implementation(project(Modules.featureRepairsPresentation))

    // AndroidX
    implementation(AndroidX.coreKtx)
    implementation(AndroidX.lifecycleRuntimeKtx)
    implementation(AndroidX.lifecycleViewModelKtx)

    // Firebase
    implementation(Firebase.firebaseAuth)
    implementation(Firebase.firebaseStorage)
    implementation(Firebase.firebaseFirestore)

    // Glide
    implementation(Glide.glide)
    kapt(Glide.glideCompiler)

    // Compose dependencies
    implementation(Compose.ui)
    implementation(Compose.uiToolingPreview)
    implementation(Compose.material)
    implementation(Compose.material3)
    implementation(Compose.activityCompose)
    implementation(Compose.viewModelCompose)
    implementation(Compose.hiltNavigationCompose)
    implementation(Compose.materialIconExtended)
    implementation(Compose.swipeRefresh)
    debugImplementation(Compose.uiTooling)

    // Compose Nav Destinations
    implementation (RaamCostaNavigation.composeDestinationsCore)
    ksp(RaamCostaNavigation.ksp)


    // Coroutines
    implementation(Coroutines.coroutinesCore)
    implementation(Coroutines.coroutinesAndroid)
    implementation(Coroutines.coroutinesPlayServices)

    //Dagger - Hilt

    implementation(DaggerHilt.hiltAndroid)
    kapt(DaggerHilt.kaptHiltAndroidCompiler)
    kapt(DaggerHilt.kaptHiltCompiler)

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