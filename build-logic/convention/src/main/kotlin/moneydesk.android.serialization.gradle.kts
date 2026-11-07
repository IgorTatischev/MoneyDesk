plugins {
    id("org.jetbrains.kotlin.plugin.serialization")
}

val catalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

dependencies {
    add("implementation", catalog.findLibrary("kotlinx-json").get())
}
