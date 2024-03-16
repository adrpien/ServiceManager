plugins {
    `android-library`
    `kotlin-android`
}

apply(from = "$rootDir/base-module.gradle")

android {
    namespace = "com.example.test"
}
dependencies{
    implementation(Testing.junit5Api)
    implementation(Testing.junit5Params)
    implementation(Testing.coroutines)
    implementation(project(Modules.featureInspectionsDomain))
    implementation(project(Modules.commonDomain))
    implementation(project(Modules.featureRepairsDomain))
    implementation(project(Modules.core))

}
