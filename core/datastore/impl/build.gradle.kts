plugins {
    id("moneydesk.android.library")
    id("moneydesk.android.hilt")
}

dependencies {
    implementation(projects.core.datastore.api)

    implementation(libs.core.ktx)
    implementation(libs.kotlinx.coroutines)
    implementation(libs.bundles.datastore.full)
}
