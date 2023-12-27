import RaamCostaNavigation.ksp

plugins {
    `android-library`
    `kotlin-android`
}

apply(from = "$rootDir/domain-module.gradle")

android {
    namespace = "com.example.feature_home_domain"
}
dependencies{

    // Apache POI
    implementation(ApachePoi.apachePoi)
    implementation(ApachePoi.apachePoiOoxml)

    // Modules
    implementation(project(Modules.core))
    implementation(project(Modules.sharedPreferences))
    implementation(project(Modules.logger))
    implementation(project(Modules.featureAppDomain))
    implementation(project(Modules.featureInspectionsDomain))

}