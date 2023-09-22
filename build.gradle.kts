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
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}