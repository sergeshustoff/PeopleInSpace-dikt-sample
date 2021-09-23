plugins {
    id("com.android.application")
    id("kotlin-android")
}

android {
    compileSdk = Versions.androidCompileSdk

    defaultConfig {
        applicationId = "com.surrus.peopleinspace"
        minSdk = 25
        targetSdk = Versions.androidTargetSdk
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Versions.compose
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
}

dependencies {

    with(Deps.AndroidX) {
        implementation(activityCompose)
    }

    with(Deps.Compose) {
        implementation(wearFoundation)
        implementation(wearMaterial)
        implementation(coilCompose)
    }

    implementation(project(":common"))
}