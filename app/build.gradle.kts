plugins {
  id("com.android.application")
  id("org.jetbrains.kotlin.android")
  id("org.jetbrains.kotlin.plugin.serialization")
}

android {
  namespace = "com.aura"
  compileSdk = 33

  defaultConfig {
    applicationId = "com.aura"
    minSdk = 24
    targetSdk = 33
    versionCode = 1
    versionName = "1.0"

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
  kotlinOptions {
    jvmTarget = "1.8"
  }
  buildFeatures {
    viewBinding = true
  }
}

dependencies {

  implementation("androidx.core:core-ktx:1.9.0")
  implementation("androidx.appcompat:appcompat:1.6.1")
  implementation("com.google.android.material:material:1.8.0")
  implementation("androidx.annotation:annotation:1.6.0")
  implementation("androidx.constraintlayout:constraintlayout:2.1.4")
  implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.3.1")
  implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1")
  testImplementation("junit:junit:4.13.2")
  androidTestImplementation("androidx.test.ext:junit:1.1.5")
  androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

  //New implementations for project
  implementation("io.ktor:ktor-client-core:3.1.2")
  implementation ("io.ktor:ktor-client-cio:3.1.2")
  implementation ("io.ktor:ktor-client-content-negotiation:3.1.2")
  implementation("io.ktor:ktor-client-logging:3.1.2")
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.10.1")
  implementation("io.ktor:ktor-serialization-kotlinx-json:3.1.2")
}