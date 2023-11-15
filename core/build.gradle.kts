import com.android.build.gradle.internal.dsl.ManagedVirtualDevice


plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    namespace = "de.tobsinger.scoreboard.core"
    compileSdk = 34
    defaultConfig {
        minSdk = 28

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
        kotlinCompilerExtensionVersion = "1.5.4"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
        packagingOptions {
            resources.excludes.add("META-INF/*")
        }
        managedDevices {
            devices {
                add(
                    ManagedVirtualDevice(name = "pixel4api30").apply {
                        device = "Pixel 4"
                        apiLevel = 30
                        systemImageSource = "aosp-atd"
                    },
                )
            }

        }
//        kotlinOptions {
//            jvmTarget = "1.8"
//        }
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    kotlin{
        jvmToolchain(jdkVersion = 17)
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")

    implementation("io.insert-koin:koin-androidx-compose:3.5.0")
    implementation(libs.koin.android)
    implementation(libs.koin.core)

    implementation("com.airbnb.android:showkase:1.0.2")
    kapt("com.airbnb.android:showkase-processor:1.0.2")

    implementation(libs.compose.ui)
    implementation(libs.compose.ui.tooling.preview)
    implementation("androidx.compose.material3:material3:1.1.2")
    implementation("androidx.compose.material:material-icons-extended:1.4.3")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.navigation:navigation-compose:2.6.0")
    implementation("com.google.accompanist:accompanist-navigation-animation:0.30.1")
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.30.1")

    implementation(project(path = ":persistence"))

    testImplementation("junit:junit:4.13.2")
    testImplementation("com.google.truth:truth:1.1.5")
    testImplementation(libs.mockk.android)
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    testImplementation("app.cash.turbine:turbine:1.0.0")

    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.4.3")
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.4.3")

    androidTestImplementation(libs.koin.test)
    androidTestImplementation(libs.koin.junit4)
    androidTestImplementation(libs.mockk.android)
}
