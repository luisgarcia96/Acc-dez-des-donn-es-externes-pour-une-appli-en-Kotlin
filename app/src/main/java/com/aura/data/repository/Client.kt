package com.aura.data.repository

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*

val client = HttpClient(CIO) {
  // Optional: Configure logging
  install(Logging) {
    logger = Logger.DEFAULT
    level = LogLevel.BODY
  }
  // Handle JSON serialization automatically
  install(ContentNegotiation) {
    json()
  }
}