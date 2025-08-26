package com.example.appvancedimport

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform