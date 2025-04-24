plugins {
    alias(libs.plugins.android.application)
    id("com.google.gms.google-services") // Firebase Google Services plugin
}

android {
    namespace = "vn.iotstar.baitap10"
    compileSdk = 35

    defaultConfig {
        applicationId = "vn.iotstar.baitap10"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // Firebase dependencies
    // Firebase Realtime Database
    implementation("com.google.firebase:firebase-database:20.3.0")
    implementation("com.firebaseui:firebase-ui-database:8.0.1")

    // Network & Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

// Gson
    implementation("com.google.code.gson:gson:2.10.1")

//    implementation("androidx.appcompat:appcompat:1.3.1") // hoặc phiên bản phù hợp với dự án của bạn
//    implementation("androidx.constraintlayout:constraintlayout:2.1.0") // hoặc phiên bản phù hợp
//    implementation("androidx.webview:webview:1.4.0") // thư viện WebView
//    // Các thư viện khác nếu cần
    /**firebase dependencies*/
    implementation ("com.google.firebase:firebase-auth:21.1.0")
    implementation ("com.google.firebase:firebase-database:20.1.0")
    implementation (platform("com.google.firebase:firebase-bom:33.12.0"))
    implementation ("com.google.firebase:firebase-database-ktx")
    implementation ("com.google.firebase:firebase-firestore:24.4.5")
    implementation ("com.google.firebase:firebase-storage:20.1.0")
    implementation ("com.firebaseui:firebase-ui-database:8.0.1")
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-auth:22.3.0")
    implementation("com.cloudinary:cloudinary-android:3.0.2")
    implementation("com.github.bumptech.glide:glide:4.16.0")


    implementation("com.cloudinary:cloudinary-android:2.4.4")
    implementation("com.google.android.exoplayer:exoplayer:2.19.1")

}
