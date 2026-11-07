plugins {
    id("moneydesk.android.library")
    id("moneydesk.android.hilt")
}

dependencies {
    implementation(projects.core.common.api)

    implementation(libs.core.ktx)
    implementation(libs.kotlinx.coroutines)
}
