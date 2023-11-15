plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
//    id("dev.chrisbanes.paparazzi") version "1.1.0-sdk33-alpha02"
    id("app.cash.paparazzi") version "1.3.1"
}

android {
    namespace = "de.tobsinger.screenshottests"
    compileSdk = 34

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.4"
    }

    defaultConfig {
        minSdk = 28
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
//    kotlin{
//        jvmToolchain(jdkVersion = 17)
//    }

}

dependencies {
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.tooling.preview)

    implementation("androidx.compose.material:material:1.4.3")

    implementation("com.airbnb.android:showkase:1.0.2")
    implementation(project(":core"))

    kapt("com.airbnb.android:showkase-processor:1.0.2")

    testImplementation("com.google.testparameterinjector:test-parameter-injector:1.14")
    testImplementation("junit:junit:4.13.2")
}
