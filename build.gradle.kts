 buildscript {
    repositories {
        mavenCentral()
        google()
    }
    dependencies {
        classpath(Build.googleServices)
        classpath(Build.hiltAndroidGradlePlugin)
        classpath(Build.androidBuildTools)
        classpath(Build.kotlinGradlePlugin)
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.0")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}