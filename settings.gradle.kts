pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
    versionCatalogs {
        create("libs") {
            version("koin", "3.5.0")
            library("koin-android", "io.insert-koin", "koin-android").versionRef("koin")
            library("koin-core", "io.insert-koin", "koin-core").versionRef("koin")
            library("koin-test", "io.insert-koin", "koin-test").versionRef("koin")
            library("koin-junit4", "io.insert-koin", "koin-test-junit4").versionRef("koin")

            version("compose", "1.4.3")
            library("compose-ui", "androidx.compose.ui", "ui").versionRef("compose")

            version("mockk", "1.13.8")
            library("mockk-android", "io.mockk","mockk-android").versionRef("mockk")

            library(
                "compose-ui-tooling-preview",
                "androidx.compose.ui",
                "ui-tooling-preview"
            ).versionRef("compose")
        }
    }
}

rootProject.name = "Scoreboard"
include(":app")
include(":screenshottests")
include(":core")
include(":persistence")
