import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlinCocoapods)
    alias(libs.plugins.kmpNativeCoroutines)
}
kotlin {
    androidTarget {
        compilations.all {
            compileTaskProvider.configure {
                compilerOptions {
                    jvmTarget.set(JvmTarget.JVM_1_8)
                }
            }
        }
    }
    nativeCoroutines {
//        // The suffix used to generate the native coroutine function and property names.
//        suffix = "Native"
//        // The suffix used to generate the native coroutine file names.
//        // Note: defaults to the suffix value when `null`.
//        fileSuffix = null
//        // The suffix used to generate the StateFlow value property names,
//        // or `null` to remove the value properties.
//        flowValueSuffix = "Value"
//        // The suffix used to generate the SharedFlow replayCache property names,
//        // or `null` to remove the replayCache properties.
//        flowReplayCacheSuffix = "ReplayCache"
//        // The suffix used to generate the native state property names.
//        stateSuffix = "Value"
//        // The suffix used to generate the `StateFlow` flow property names,
//        // or `null` to remove the flow properties.
        stateFlowSuffix = "Flow"
    }
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    )

    cocoapods {
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        version = "1.0"
        ios.deploymentTarget = "16.1"

        framework {
            baseName = "shared"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            // Koin Core is a true multiplatform library
            api(libs.koin.core)

            // These are also true multiplatform libraries
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.bundles.ktor)
            implementation(libs.kmp.nativecoroutines.core)
        }

        androidMain.dependencies {
            implementation(libs.ktor.client.okhttp.v2311)
            implementation(libs.kotlinx.coroutines.android)

            implementation(libs.koin.android)
            implementation(libs.koin.compose.viewmodel)
            implementation(libs.lifecycle.viewmodel)
            implementation(libs.navigation.compose)
        }

        // nativeMain is for all native targets (ios, macos, etc)
        nativeMain.dependencies {
            // Darwin is the Ktor engine for Apple platforms
            implementation(libs.ktor.client.darwin)
        }

        iosMain.dependencies {
            // You can add iOS-specific Koin helpers if needed, but core is usually enough
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

android {
    namespace = "com.friosteven.rosebedsheet_kmp"
    compileSdk = 34
    defaultConfig {
        minSdk = 28
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}