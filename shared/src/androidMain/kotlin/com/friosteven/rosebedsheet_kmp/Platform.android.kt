package com.friosteven.rosebedsheet_kmp

import android.util.Log
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.logging.Logger
import org.koin.dsl.module


actual fun platformModule() = module {
    single<HttpClientEngine> { OkHttp.create() }
}

actual fun createPlatformLogger(): Logger = object : Logger {
    override fun log(message: String) {
        Log.d("HttpClient", message)
    }
}