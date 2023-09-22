 buildscript {
    repositories {
        mavenCentral()
        google()
    }
    dependencies {
        classpath("com.google.gms:google-services:4.3.14")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.43.2")
        //classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.10")
        //classpath("com.android.tools.build:gradle:8.1.1")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}