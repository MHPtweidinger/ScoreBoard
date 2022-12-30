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
            version("koin", "3.3.2")
            library("koin-android", "io.insert-koin", "koin-android").versionRef("koin")
            library("koin-core", "io.insert-koin", "koin-core").versionRef("koin")

            version("compose", "1.3.2")
            library("compose-ui", "androidx.compose.ui", "ui").versionRef("compose")
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
