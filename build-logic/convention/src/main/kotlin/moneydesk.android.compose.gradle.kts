import com.android.build.api.dsl.LibraryExtension

plugins {
    id("moneydesk.android.library")
    id("org.jetbrains.kotlin.plugin.compose")
}

val catalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

extensions.configure<LibraryExtension> {
    buildFeatures {
        compose = true
    }
}

dependencies {
    add("implementation", platform(catalog.findLibrary("compose-bom").get()))
    add("implementation", catalog.findLibrary("ui").get())
    add("implementation", catalog.findLibrary("ui-graphics").get())
    add("implementation", catalog.findLibrary("ui-tooling-preview").get())
    add("implementation", catalog.findLibrary("material3").get())
    add("debugImplementation", catalog.findLibrary("ui-tooling").get())
}
