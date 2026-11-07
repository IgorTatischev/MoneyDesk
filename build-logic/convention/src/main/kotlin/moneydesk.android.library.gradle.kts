import com.android.build.api.dsl.LibraryExtension
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    id("com.android.library")
}

val catalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

/** :feature:main:api -> com.money.main.api, :core:ui -> com.money.ui */
val moduleNamespace = if (project.name == "api" || project.name == "impl") {
    "com.money." + project.parent!!.name + "." + project.name
} else {
    "com.money." + project.name
}

extensions.configure<LibraryExtension> {
    namespace = moduleNamespace
    compileSdk = catalog.findVersion("compile-sdk").get().requiredVersion.toInt()

    defaultConfig {
        minSdk = catalog.findVersion("min-sdk").get().requiredVersion.toInt()
        if (file("consumer-rules.pro").exists()) consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = catalog.findVersion("release_minify").get().requiredVersion.toBoolean()
            if (file("proguard-rules.pro").exists()) {
                proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            }
        }
        debug {
            isMinifyEnabled = catalog.findVersion("debug-minify").get().requiredVersion.toBoolean()
            if (file("proguard-rules.pro").exists()) {
                proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            }
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

extensions.configure<org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension>("kotlin") {
    compilerOptions {
        jvmTarget.set(JvmTarget.fromTarget(catalog.findVersion("java-versions").get().requiredVersion))
    }
}
