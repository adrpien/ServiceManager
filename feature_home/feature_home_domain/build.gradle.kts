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
    implementation(ApachePoi.staxApi)
    implementation(ApachePoi.aaltoXml)
    implementation(ApachePoi.xmlBeans)

    // Modules
    implementation(project(Modules.core))
    implementation(project(Modules.sharedPreferences))
    implementation(project(Modules.logger))
    implementation(project(Modules.commonDomain))
    implementation(project(Modules.featureInspectionsDomain))

}