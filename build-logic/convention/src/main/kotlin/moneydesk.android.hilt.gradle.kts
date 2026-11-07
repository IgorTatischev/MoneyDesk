plugins {
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
}

val catalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

dependencies {
    add("implementation", catalog.findLibrary("dagger-hilt").get())
    add("ksp", catalog.findLibrary("dagger-hilt-compiler").get())
}
