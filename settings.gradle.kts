pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Personal Finance Tracker"
include(":app")
include(":data")
include(":domain")
include(":presentation")
include(":currency_conversion")
include(":currency_conversion:data")
include(":currency_conversion:domain")
include(":currency_conversion:presentation")
include(":currency_conversion:navigation")
include(":base")
