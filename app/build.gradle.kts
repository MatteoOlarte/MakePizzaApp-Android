import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.google.services)
    alias(libs.plugins.google.firebase.crashlytics)
    alias(libs.plugins.google.devtools.ksp)
}

android {
    val properties = Properties()
    val file = File(rootDir, "secret.properties")

    if (file.exists() && file.isFile) {
        file.inputStream().use { properties.load(it) }
    }

    namespace = "com.example.makepizza_android"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.makepizza_android"
        minSdk = 29
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        ksp {
            artifacts {
                arg("room.schemaLocation", "$projectDir/sql-schemas")
            }
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "MAKE_PIZZA_API_URL", properties.getProperty("PRD_MAKE_PIZZA_API_URL"))
        }

        debug {
            buildConfigField("String", "MAKE_PIZZA_API_URL", properties.getProperty("DEV_MAKE_PIZZA_API_URL"))
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.material.icons.extended)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.runtime.livedata)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)
    implementation(platform(libs.google.firebase.boom))
    implementation(libs.google.firebase.crashlytics)
    implementation(libs.google.firebase.auth)
    implementation(libs.voyager.navigator)
    implementation(libs.voyager.tabNavigator)
    implementation(libs.voyager.transitions)
    implementation(libs.voyager.screenModel)
    implementation(libs.retrofit2.retrofit)
    implementation(libs.retrofit2.converter.gson)
    implementation(libs.kotlinx.corroutines.android)
    implementation(libs.valentinilk.shimmer.compose)
    implementation(libs.coil.compose)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation("com.squareup.okhttp3:okhttp:4.11.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")
    implementation("com.google.code.gson:gson:2.10.1")
}
