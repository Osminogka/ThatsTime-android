plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.thatstime"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.thatstime"
        minSdk = 24
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
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

    // Retrofit for networking
    implementation("com.squareup.retrofit2:retrofit:2.9.0")

    // Gson converter for parsing JSON
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // OkHttp logging interceptor (optional, for logging HTTP requests)
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.0")
}