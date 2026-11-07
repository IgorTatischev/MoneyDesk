plugins {
    id("moneydesk.android.compose")
    id("moneydesk.android.serialization")
    id("moneydesk.android.hilt")
}

dependencies {
    implementation(projects.feature.authorization.api)
    implementation(projects.core.common.api)
    implementation(projects.core.common.impl)
    implementation(projects.core.ui)
    implementation(projects.core.datastore.api)

    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.lifecycle.runtime.compose)
    implementation(libs.dagger.hilt.navigation)
    implementation(libs.identity.googleid)
    implementation(libs.google.material)
    implementation(libs.androidx.appcompat)
    implementation(libs.navigation.compose)
    implementation(libs.activity.compose)
    implementation(libs.androidx.window)
    implementation(libs.extended.icons)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.auth)
    implementation(libs.play.services.auth)
    implementation(libs.androidx.credentials.play.services.auth)
    implementation(libs.androidx.credentials)
}
