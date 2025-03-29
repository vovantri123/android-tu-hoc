plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "vn.iostar.handler_asynctask_jsonapi"
    compileSdk = 35

    defaultConfig {
        applicationId = "vn.iostar.handler_asynctask_jsonapi"
        minSdk = 21
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
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    //thu viện load image
    implementation("com.github.bumptech.glide:glide:4.14.2")
    annotationProcessor("com.github.bumptech.glide:compiler:4.14.2")
    //thu viện load dữ liệu API
    implementation("com.android.volley:volley:1.2.1")
    //thư viện circle images
    //bo goc tron cho ImageView
    implementation("de.hdodenhof:circleimageview:3.1.0")
}