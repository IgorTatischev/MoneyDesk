enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
pluginManagement {
    includeBuild("build-logic")
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
include(":core:common:api")
include(":core:common:impl")
include(":core:ui")
include(":core:datastore:api")
include(":core:datastore:impl")
include(":feature:settings:api")
include(":feature:settings:impl")
include(":feature:wallet:api")
include(":feature:wallet:impl")
include(":feature:categories:api")
include(":feature:categories:impl")
include(":feature:authorization:api")
include(":feature:authorization:impl")
include(":feature:main:api")
include(":feature:main:impl")
