import java.util.Properties
import kotlin.apply

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.dagger.hilt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.fajar.cinescope.core.data"
    compileSdk {
        version = release(37) {
            minorApiLevel = 1
        }
    }

    fun getLocalProperties(): Properties {
        return Properties().apply {
            val localPropertiesFile = rootProject.file("local.properties")
            if(localPropertiesFile.exists()){
                this.load(localPropertiesFile.inputStream())
            }
        }
    }

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"


        val localProperties = getLocalProperties()
        val apiAccessToken = localProperties.getProperty("API_ACCESS_TOKEN", "")

        buildConfigField("String", "BASE_URL", "\"https://api.themoviedb.org/3/movie/\"")
        buildConfigField("String", "BASE_IMAGE_URL", "\"https://image.tmdb.org/t/p/original/\"")
        buildConfigField("String", "API_ACCESS_TOKEN", "\"$apiAccessToken\"")
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(project(":core:domain"))
    implementation(project(":core:common"))
    testImplementation(project(":core:test-utils"))

    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutine.test)
    testImplementation(libs.kotlin.test)
    testImplementation(libs.mockk)
    implementation(libs.kotlin.stdlib)
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.okhttp)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.client.logging)
    implementation(libs.androidx.annotation.experimental)

    // DI (Dagger Hilt)
    implementation(libs.dagger.hilt)
    ksp(libs.dagger.hilt.compiler)

    // Local SQL Database (Room)
    implementation(libs.androidx.room3.runtime)
    ksp(libs.androidx.room3.compiler)
}