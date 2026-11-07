plugins {
    id("moneydesk.android.library")
    id("moneydesk.android.serialization")
}

dependencies {
    api(projects.core.common.api)
    implementation(libs.androidx.navigation.runtime.ktx)
}
