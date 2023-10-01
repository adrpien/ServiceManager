plugins {
    id("com.google.devtools.ksp") version "1.8.10-1.0.9" apply false

}
buildscript {
    repositories {
        mavenCentral()
        google()
    }
    dependencies {
        classpath(Build.androidBuildTools)
        classpath(Build.hiltAndroidGradlePlugin)
        classpath(Build.kotlinGradlePlugin)
        classpath(Build.googleServices)
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}