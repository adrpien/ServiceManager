 buildscript {
    repositories {
        mavenCentral()
        google()
    }
    dependencies {
        classpath(Build.googleServices)
        classpath(Build.hiltAndroidGradlePlugin)
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}