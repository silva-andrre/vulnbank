plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.vulnbank.app"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.vulnbank.app"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        // M1 - Credenciais movidas para BuildConfig
        buildConfigField("String", "ADMIN_USER", "\"admin\"")
        buildConfigField("String", "ADMIN_PASS", "\"admin123\"")
    }

    buildFeatures {
        buildConfig = true
    }

    buildTypes {
        release {
            // M7 - Ofuscação habilitada
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            // M7 - Debug seguro
            isDebuggable = false
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    // M2 - Dependências atualizadas
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.activity.ktx)

    // M9 - Criptografia para SharedPreferences
    implementation(libs.androidx.security.crypto)

    // M10 - BCrypt para hash seguro de senhas
    implementation("org.mindrot:jbcrypt:0.4")

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
