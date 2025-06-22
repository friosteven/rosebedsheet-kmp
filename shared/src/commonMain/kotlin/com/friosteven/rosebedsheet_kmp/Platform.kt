package com.friosteven.rosebedsheet_kmp

import io.ktor.client.plugins.logging.Logger
import org.koin.core.module.Module


expect fun platformModule(): Module

expect fun createPlatformLogger(): Logger
