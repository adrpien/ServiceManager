
plugins {
    `android-library`
    `kotlin-android`
}

apply(from = "$rootDir/domain-module.gradle")

android {
    namespace = "com.example.logger_domain"
}
dependencies{
    implementation(project(Modules.core))
    implementation(project(Modules.featureInspectionsDomain))
    implementation(project(Modules.featureRepairsDomain))
}