plugins {
    id("com.google.devtools.ksp") version "1.8.0-1.0.9"
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
        classpath("com.squareup:javapoet:1.13.0")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

tasks.withType<Test> {
    useJUnitPlatform()
}
