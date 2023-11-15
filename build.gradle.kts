buildscript {

}

plugins {
    id("org.jetbrains.kotlin.plugin.serialization") version embeddedKotlinVersion apply false
    id("com.android.library") version "8.1.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.20" apply false
    id("com.android.application") version "8.1.0" apply false
}

subprojects {
    repositories {
        mavenCentral()
        google()
        maven { url = uri("https://jitpack.io") }
    }
}
