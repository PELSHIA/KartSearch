plugins {
    id ("com.android.library")
    id ("org.jetbrains.kotlin.android")
    id ("kotlin-kapt")
}

val key: String = com.android.build.gradle.internal.cxx.configure.gradleLocalProperties(rootDir).getProperty("api_key")

android {
    compileSdk = Config.compileSdk

    defaultConfig {
        minSdk = Config.minSdk
        targetSdk = Config.target

        testInstrumentationRunner = Config.testInstrumentationRunner
//        consumerProguardFiles = "consumer-rules.pro"
        buildConfigField ("String", "API_KEY", getApiKey("api_key"))
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles (getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        getByName("debug") {
            buildConfigField("String", "API_KEY", key)
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

fun getApiKey(propertyKey: String): String {
    return com.android.build.gradle.internal.cxx.configure.gradleLocalProperties(rootDir).getProperty(propertyKey)
}

dependencies {

    implementation(project(":domain"))

    implementation (Dependency.Library.AndroidX.core)
    implementation (Dependency.Library.AndroidX.appcompat)
    implementation (Dependency.Library.AndroidX.material)
    testImplementation (Dependency.Library.Test.junit_test)
    androidTestImplementation (Dependency.Library.Test.junit)
    androidTestImplementation (Dependency.Library.AndroidX.espresso)

    /* Room */
    kapt (Dependency.Library.Room.room_compiler)
    api (Dependency.Library.Room.room_runtime)
    implementation (Dependency.Library.Room.room_ktx)

    /* Retrofit2 */
    implementation (Dependency.Library.Retrofit2.retrofit2)
    implementation (Dependency.Library.Retrofit2.retrofit2_gson)
    implementation (Dependency.Library.Retrofit2.logging)

}