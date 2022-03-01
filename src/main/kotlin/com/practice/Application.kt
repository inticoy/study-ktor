package com.practice

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.practice.plugins.*

fun main() {
    embeddedServer(Netty, port = PORT, host = HOST) {
        configureRouting()
        configureSecurity()
        configureSerialization()
    }.start(wait = true)
}
