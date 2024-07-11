import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
  alias(libs.plugins.androidApplication)
  alias(libs.plugins.jetbrainsKotlinAndroid)
  id("org.jetbrains.kotlin.plugin.serialization")
  id("com.google.devtools.ksp")
  id("com.google.gms.google-services")
  id("com.google.firebase.crashlytics")
}

val riotApiKey: String = gradleLocalProperties(rootDir, providers).getProperty("riotApiKey")

android {
  namespace = "cz.cvut.fit.tomanma9"
  compileSdk = 34
  buildFeatures.buildConfig = true

  defaultConfig {
    applicationId = "cz.cvut.fit.tomanma9"
    minSdk = 24
    targetSdk = 34
    versionCode = 1
    versionName = "1.0"

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    vectorDrawables { useSupportLibrary = true }
    buildConfigField("String", "RIOT_API_KEY", riotApiKey)
  }

  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
  }
  kotlinOptions { jvmTarget = "1.8" }
  buildFeatures { compose = true }
  composeOptions { kotlinCompilerExtensionVersion = "1.5.11" }
  packaging { resources { excludes += "/META-INF/{AL2.0,LGPL2.1}" } }
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
  testImplementation(libs.junit)
  androidTestImplementation(libs.androidx.junit)
  androidTestImplementation(libs.androidx.espresso.core)
  androidTestImplementation(platform(libs.androidx.compose.bom))
  androidTestImplementation(libs.androidx.ui.test.junit4)
  debugImplementation(libs.androidx.ui.tooling)
  debugImplementation(libs.androidx.ui.test.manifest)

  implementation(libs.coil.compose)

  implementation(libs.androidx.navigation.compose)

  implementation(libs.okhttp)
  implementation(libs.logging.interceptor)
  implementation(libs.retrofit)
  implementation(libs.kotlinx.serialization.json)
  implementation(libs.retrofit2.kotlinx.serialization.converter)

  implementation(libs.androidx.room.ktx)
  ksp(libs.androidx.room.compiler)

  implementation(platform(libs.koin.bom))
  implementation(libs.koin.android)
  implementation(libs.koin.android.compose)
  implementation(libs.koin.androidx.workmanager)

  implementation(libs.androidx.work.runtime.ktx)

  implementation(libs.accompanist.permissions)

  implementation(platform("com.google.firebase:firebase-bom:33.0.0"))
  implementation("com.google.firebase:firebase-analytics")
  implementation("com.google.firebase:firebase-crashlytics")
}
