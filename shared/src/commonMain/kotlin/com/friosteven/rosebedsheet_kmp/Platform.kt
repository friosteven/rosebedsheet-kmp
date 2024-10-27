package com.friosteven.rosebedsheet_kmp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform