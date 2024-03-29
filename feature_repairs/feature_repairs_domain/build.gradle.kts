plugins {
    `android-library`
    `kotlin-android`
}

apply(from = "$rootDir/domain-module.gradle")

android {
    namespace = "com.example.feature_repairs_domain"
}
dependencies{
    implementation(project(Modules.core))
    implementation(project(Modules.sharedPreferences))
    implementation(project(Modules.logger))
    implementation(project(Modules.commonDomain))
    testImplementation(project(Modules.test))
}