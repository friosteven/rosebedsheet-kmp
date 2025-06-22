package com.friosteven.rosebedsheet_kmp

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.logging.Logger
import platform.UIKit.UIDevice
import platform.Foundation.NSLog

import io.ktor.client.engine.darwin.Darwin
import org.koin.dsl.module

actual fun platformModule() = module {
    single<HttpClientEngine> { Darwin.create() } // Provides the Ktor engine for iOS
}

actual fun createPlatformLogger(): Logger = object : Logger {
    override fun log(message: String) {
        // Or use a dedicated KMP logging library like Napier
        NSLog(message)
    }
}