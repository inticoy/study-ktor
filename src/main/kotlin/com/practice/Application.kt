package com.practice

import com.practice.database.configureDatabase
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.practice.plugins.*
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

fun main() {
    embeddedServer(Netty, port = PORT, host = HOST) {
        configureDatabase()
        configureSecurity()
        configureRouting()
        configureSerialization()
    }.start(wait = true)
}
