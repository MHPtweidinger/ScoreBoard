import com.android.build.gradle.internal.dsl.ManagedVirtualDevice


plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    namespace = "de.tobsinger.scoreboard.core"
    compileSdk = 33
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
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.1.1"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
        packagingOptions {
            resources.excludes.add("META-INF/*")
        }
        managedDevices {
            devices{
                maybeCreate<com.android.build.api.dsl.ManagedVirtualDevice>("pixel2api30").apply {
                    // Use device profiles you typically see in Android Studio.
                    device = "Pixel 2"
                    // ATDs currently support only API level 30.
                    apiLevel = 30
                    // You can also specify "google-atd" if you require Google Play Services.
                    systemImageSource = "aosp-atd"
                }
            }

        }
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.0")

    implementation("io.insert-koin:koin-androidx-compose:3.4.1")
    implementation(libs.koin.android)
    implementation(libs.koin.core)

    implementation("com.airbnb.android:showkase:1.0.0-kotlin1.5.30-1")
    kapt("com.airbnb.android:showkase-processor:1.0.0-kotlin1.5.30-1")

    implementation(libs.compose.ui)
    implementation(libs.compose.ui.tooling.preview)
    implementation("androidx.compose.material3:material3:1.0.1")
    implementation("androidx.compose.material:material-icons-extended:1.3.1")
    implementation("com.google.android.material:material:1.7.0")
    implementation("androidx.navigation:navigation-compose:2.5.3")
    implementation("com.google.accompanist:accompanist-navigation-animation:0.28.0")
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.28.0")

    implementation(project(path = ":persistence"))

    testImplementation("junit:junit:4.13.2")
    testImplementation("com.google.truth:truth:1.1.3")
    testImplementation(libs.mockk.android)
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")
    testImplementation("app.cash.turbine:turbine:0.12.1")

    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.3.3")
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.4.0-alpha04")

    androidTestImplementation(libs.koin.test)
    androidTestImplementation(libs.koin.junit4)
    androidTestImplementation(libs.mockk.android)
}
