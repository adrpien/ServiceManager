plugins {
    `android-library`
    `kotlin-android`
}

apply(from = "$rootDir/data-module.gradle")

android {
    namespace = "com.example.logger_data"
}
dependencies{

    implementation(project(Modules.core))
    implementation(project(Modules.loggerDomain))
    implementation(project(Modules.featureAppDomain))
    implementation(project(Modules.featureInspectionsDomain))
    implementation(project(Modules.featureRepairsDomain))
}