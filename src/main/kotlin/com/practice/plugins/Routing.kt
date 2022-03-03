package com.practice.plugins

import com.practice.routing.v1Routing
import io.ktor.server.routing.*
import io.ktor.server.application.*

fun Application.configureRouting() {
    routing {
        route("v1") {
            v1Routing()
        }
    }
}
