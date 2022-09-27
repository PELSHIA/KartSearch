import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id ("com.android.application")
    id ("kotlin-android")
    id ("kotlin-kapt")
    id ("androidx.navigation.safeargs")
    id ("com.google.gms.google-services")
    id ("dagger.hilt.android.plugin")
}

//Properties properties = new Properties()
//properties.load(project.rootProject.file("local.properties").newDataInputStream())

android {
    compileSdk = Config.compileSdk

    defaultConfig {
        applicationId = Config.applicationId
        minSdk = Config.minSdk
        targetSdk = Config.target
        versionCode = Config.version
        versionName = Config.versionName

        testInstrumentationRunner = Config.testInstrumentationRunner
        buildConfigField ("String", "API_KEY", getApiKey("api_key"))
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = Config.jvmTarget
    }
    buildFeatures {
        dataBinding = true
        viewBinding = true
    }
    kapt {
        javacOptions {
            option("Adagger.fastInit", "ENABLED")
            option("Adagger.hilt.android.internal.disableAndroidSuperclassValidation", "true")
        }
    }
}

fun getApiKey(propertyKey: String): String {
    return gradleLocalProperties(rootDir).getProperty(propertyKey)
}

dependencies {

    implementation(project(":data"))
    implementation(project(":domain"))

    implementation (Dependency.Library.AndroidX.core)
    implementation (Dependency.Library.AndroidX.appcompat)
    implementation (Dependency.Library.AndroidX.material)
    implementation (Dependency.Library.AndroidX.constraint_layout)
    implementation (Dependency.Library.AndroidX.legacy)
    testImplementation (Dependency.Library.Test.junit_test)
    androidTestImplementation (Dependency.Library.Test.junit)
    androidTestImplementation (Dependency.Library.AndroidX.espresso)

    /* Navigation Component */
    implementation (Dependency.Library.Navigation.navigation_fragment)
    implementation (Dependency.Library.Navigation.navigation_ui)

    /* Retrofit2 */
    implementation (Dependency.Library.Retrofit2.retrofit2)
    implementation (Dependency.Library.Retrofit2.retrofit2_gson)
    implementation (Dependency.Library.Retrofit2.logging)

    /* Glide */
    implementation (Dependency.Library.Glide.glide)
    annotationProcessor (Dependency.Library.Glide.glide_compiler)

    /* FireBase */
    implementation (platform(Dependency.Library.Firebase.firebase_bom))
    implementation (Dependency.Library.Firebase.firebase_analytics)
    implementation (Dependency.Library.Firebase.firebase_storage)
    implementation (Dependency.Library.Firebase.firebase_storage_ktx)
    implementation (Dependency.Library.Firebase.firebase_database_ktx)

    /* Jsoup */
    implementation (Dependency.Library.Jsoup.jsoup)

    /*  Dagger-Hilt */
    implementation (Dependency.Library.DaggerHilt.hilt_android)
    implementation (Dependency.Library.DaggerHilt.hilt_common)
    implementation (Dependency.Library.DaggerHilt.hilt_lifecycle)
    kapt (Dependency.Library.DaggerHilt.hilt_google_compiler)
    kapt (Dependency.Library.DaggerHilt.hilt_android_compiler)

    /* Room */
    kapt (Dependency.Library.Room.room_compiler)
    implementation (Dependency.Library.Room.room_runtime)
    implementation (Dependency.Library.Room.room_ktx)

    /* SwipeRefreshLayout */
    implementation (Dependency.Library.AndroidX.swipeRefreshLayout)

    /* MPAndroidChart */
    implementation (Dependency.Library.MPAndroidChart.mpAndroidChart)

    /* Coroutines */
    implementation (Dependency.Library.Coroutines.coroutines)

    /* Lottie */
    implementation (Dependency.Library.Lottie.lottie)

}