plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "de.tobsinger.scoreboard"
    compileSdk = 34
    defaultConfig {
        applicationId = "de.tobsinger.scoreboard"
        minSdk = 28
        versionCode = 2
        versionName = "1.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
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
    kotlin{
        jvmToolchain(jdkVersion = 17)
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.4"
    }
    packagingOptions {
        resources.excludes += "/META-INF/{AL2.0,LGPL2.1}"
    }
}

object dependencies {
    const val compose_version = "1.3.2"
    const val koin_version = "3.3.2"
}

dependencies {

    implementation(libs.koin.android)
    implementation(libs.koin.core)

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.8.0")
    implementation(project(":core"))

    implementation(project(":persistence"))

    testImplementation("junit:junit:4.13.2")
}
