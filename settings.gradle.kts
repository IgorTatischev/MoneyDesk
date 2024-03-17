enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
pluginManagement {
    repositories {
        google()
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

rootProject.name = "MoneyDesk"
include(":app")
include(":core:common")
include(":core:navigation")
include(":core:ui")
include(":core:datastore")
include(":feature:settings")
include(":feature:wallet")
include(":feature:categories")
include(":feature:authorization")
include(":feature:main")
