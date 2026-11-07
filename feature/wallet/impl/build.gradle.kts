plugins {
    id("moneydesk.android.compose")
    id("moneydesk.android.serialization")
}

dependencies {
    implementation(projects.feature.wallet.api)
    implementation(projects.core.common.api)
    implementation(projects.core.common.impl)
    implementation(projects.core.ui)

    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.lifecycle.runtime.compose)
    implementation(libs.google.material)
    implementation(libs.androidx.appcompat)
    implementation(libs.navigation.compose)
    implementation(libs.activity.compose)
    implementation(libs.androidx.window)
}
