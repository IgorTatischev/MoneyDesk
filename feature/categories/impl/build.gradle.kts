plugins {
    id("moneydesk.android.compose")
    id("moneydesk.android.serialization")
    id("moneydesk.android.hilt")
}

dependencies {
    implementation(projects.feature.categories.api)
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
    implementation(libs.extended.icons)
    implementation(libs.dagger.hilt.navigation)
    implementation(libs.identity.googleid)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.auth)

    implementation(libs.compose.dialogs.core)
    implementation(libs.compose.dialogs.color)
    implementation(libs.compose.dialogs.emoji)
    implementation(libs.androidx.emoji2)
    implementation(libs.androidx.emoji2.bundled)
}
