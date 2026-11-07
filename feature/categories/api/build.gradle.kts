plugins {
    id("moneydesk.android.library")
    id("moneydesk.android.serialization")
}

dependencies {
    api(projects.core.common.api)
    api(libs.kotlinx.coroutines)
    implementation(libs.core.ktx)
    implementation(libs.androidx.navigation.runtime.ktx)
}
