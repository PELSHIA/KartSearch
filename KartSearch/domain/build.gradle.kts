plugins {
    id ("com.android.library")
    id ("org.jetbrains.kotlin.android")
}

android {
    compileSdk = Config.compileSdk

    defaultConfig {
        minSdk = Config.minSdk
        targetSdk = Config.target

        testInstrumentationRunner = Config.testInstrumentationRunner
//        consumerProguardFiles = "consumer-rules.pro"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles (getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = Config.jvmTarget
    }
}

dependencies {

    implementation (Dependency.Library.AndroidX.core)
    implementation (Dependency.Library.AndroidX.appcompat)
    implementation (Dependency.Library.AndroidX.material)
    testImplementation (Dependency.Library.Test.junit_test)
    androidTestImplementation (Dependency.Library.Test.junit)
    androidTestImplementation (Dependency.Library.AndroidX.espresso)

}