buildscript {

}

plugins {
    id("org.jetbrains.kotlin.plugin.serialization") version embeddedKotlinVersion apply false
    id("com.android.library") version "7.3.1" apply false
    id("org.jetbrains.kotlin.android") version "1.6.10" apply false
    id("com.android.application") version "7.3.1" apply false
}

subprojects {
    repositories {
        mavenCentral()
        google()
        maven { url = uri("https://jitpack.io") }
    }
}
