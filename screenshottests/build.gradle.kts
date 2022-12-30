plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dev.chrisbanes.paparazzi") version "1.1.0-sdk33-alpha02"
}

android {
    namespace = "de.tobsinger.screenshottests"
    compileSdk = 33

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.1.1"
    }

    defaultConfig {
        minSdk = 21
        targetSdk = 33
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.tooling.preview)

    implementation("androidx.compose.material:material:1.3.1")

    implementation("com.airbnb.android:showkase:1.0.0-kotlin1.5.30-1")

    implementation(project(":scoreboard"))

    kapt("com.airbnb.android:showkase-processor:1.0.0-kotlin1.5.30-1")

    testImplementation("com.google.testparameterinjector:test-parameter-injector:1.10")
    testImplementation("junit:junit:4.13.2")
}
