object Dependency {
    object Versions {
        const val core = "1.7.0"
        const val appCompat = "1.5.1"
        const val material = "1.6.1"
        const val constraint_layout = "2.1.4"
        const val legacy = "1.0.0"
        const val junit = "1.1.3"
        const val espresso = "3.4.0"

        const val navigation = "2.5.2"
        const val retrofit2 = "2.9.0"
        const val logging = "4.9.2"
        const val glide = "4.12.0"
        const val firebase_bom = "29.0.1"
        const val firebase_storage = "20.0.2"
        const val firebase_analytics = "21.1.1"
        const val firebase_database = "20.0.6"
        const val jsoup = "1.13.1"
        const val daggerHilt_google = "2.37"
        const val daggerHilt_android = "1.0.0"
        const val daggerHilt_lifecycle = "1.0.0-alpha03"
        const val room = "2.4.3"
        const val swipeRefreshLayout = "1.1.0"
        const val MPAndroidChart = "v3.1.0"
        const val coroutines = "1.6.3"
        const val lottie = "4.1.0"
    }

    object Library {
        object AndroidX {
            const val core = "androidx.core:core-ktx:${Versions.core}"
            const val appcompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
            const val material = "com.google.android.material:material:${Versions.material}"
            const val constraint_layout = "androidx.constraintlayout:constraintlayout:${Versions.constraint_layout}"
            const val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"
            const val legacy = "androidx.legacy:legacy-support-v4:${Versions.legacy}"
            const val swipeRefreshLayout = "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.swipeRefreshLayout}"
        }

        object Test {
            const val junit = "androidx.test.ext:junit:${Versions.junit}"
            const val junit_test = "junit:junit:4.+"
        }

        object Navigation {
            const val navigation_fragment = "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
            const val navigation_ui = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"
        }

        object Retrofit2 {
            const val retrofit2 = "com.squareup.retrofit2:retrofit:${Versions.retrofit2}"
            const val retrofit2_gson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit2}"
            const val logging = "com.squareup.okhttp3:logging-interceptor:${Versions.logging}"
        }

        object Glide {
            const val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
            const val glide_compiler = "com.github.bumptech.glide:compiler:${Versions.glide}"
        }

        object Firebase {
            const val firebase_bom = "com.google.firebase:firebase-bom:${Versions.firebase_bom}"
            const val firebase_analytics = "com.google.firebase:firebase-analytics-ktx:${Versions.firebase_analytics}"
            const val firebase_storage = "com.google.firebase:firebase-storage:${Versions.firebase_storage}"
            const val firebase_storage_ktx = "com.google.firebase:firebase-storage-ktx:${Versions.firebase_storage}"
            const val firebase_database_ktx = "com.google.firebase:firebase-database-ktx:${Versions.firebase_database}"
        }

        object Jsoup {
            const val jsoup = "org.jsoup:jsoup:${Versions.jsoup}"
        }

        object DaggerHilt {
            const val hilt_android = "com.google.dagger:hilt-android:${Versions.daggerHilt_google}"
            const val hilt_common = "com.google.dagger:hilt-android:${Versions.daggerHilt_android}"
//            const val hilt_lifecycle = "androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha03"
            const val hilt_lifecycle = "androidx.hilt:hilt-lifecycle-viewmodel:${Versions.daggerHilt_lifecycle}"
            const val hilt_google_compiler = "com.google.dagger:hilt-android-compiler:${Versions.daggerHilt_google}"
            const val hilt_android_compiler = "androidx.hilt:hilt-compiler:${Versions.daggerHilt_android}"
        }

        object Room {
            const val room_runtime = "androidx.room:room-runtime:${Versions.room}"
            const val room_ktx = "androidx.room:room-ktx:${Versions.room}"
            const val room_compiler = "androidx.room:room-compiler:${Versions.room}"
        }

        object MPAndroidChart {
            const val mpAndroidChart = "com.github.PhilJay:MPAndroidChart:${Versions.MPAndroidChart}"
        }

        object Coroutines {
            const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
        }

        object Lottie {
            const val lottie = "com.airbnb.android:lottie:${Versions.lottie}"
        }
    }
}