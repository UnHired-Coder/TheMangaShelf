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

rootProject.name = "TheMangaShelf"
include(":app")
include(":feature:listmanga")
include(":core:network")
include(":core:database")
include(":core:common")
include(":core:data")
include(":core:domain")
include(":core:di")
include(":core:ui")
include(":feature:mangadetails")
