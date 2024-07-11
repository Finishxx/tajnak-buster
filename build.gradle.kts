// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
  alias(libs.plugins.androidApplication) apply false
  alias(libs.plugins.jetbrainsKotlinAndroid) apply false
  id("org.jetbrains.kotlin.plugin.serialization") version "1.9.23" apply false
  id("com.google.devtools.ksp") version "1.9.23-1.0.19" apply false
  id("com.google.gms.google-services") version "4.4.1" apply false
  id("com.google.firebase.crashlytics") version "3.0.1" apply false
}
