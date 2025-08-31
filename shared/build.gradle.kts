import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget





plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)

   // kotlin("multiplatform") version "2.2.0" line8
   // kotlin("plugin.serialization") version "2.2.0"
// id("org.jetbrains.kotlin.plugin.serialization") version "2.2.20-RC"???


    id("com.google.devtools.ksp") version "2.2.10-2.0.2"
    id("com.rickclephas.kmp.nativecoroutines") version "1.0.0-ALPHA-46"

}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "Shared"
            isStatic = true
        }
    }
    
    sourceSets {
        repositories {
            mavenCentral()
        }

        all {
            languageSettings.optIn("kotlinx.cinterop.ExperimentalForeignApi")
            languageSettings.optIn("kotlin.experimental.ExperimentalObjCName")
        }

        commonMain.dependencies {
            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)
            implementation(libs.koin.compose.viewmodel.navigation)


            implementation(libs.ktor.client.core)

            implementation(libs.ktor.client.core)
            implementation(libs.kotlinx.coroutines.core)

            implementation("io.ktor:ktor-serialization-kotlinx-json:3.2.3")
            implementation("io.ktor:ktor-client-content-negotiation:3.2.3")

            implementation(libs.kotlinx.coroutines.core)

            implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.9.0")

            api("com.rickclephas.kmp:kmp-observableviewmodel-core:1.0.0-BETA-13")
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)

        }
        androidMain.dependencies {
            implementation(libs.ktor.client.okhttp)

            implementation(libs.kotlinx.coroutines.android)

        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
    }
}

android {
    namespace = "com.example.appvancedimport.shared"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}
