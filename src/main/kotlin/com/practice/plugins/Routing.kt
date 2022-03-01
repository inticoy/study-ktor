package com.practice.plugins

import com.practice.User
import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.request.*

fun Application.configureRouting() {

    routing {
        get("/") {
            call.respondText("Hello World!")
        }
        post("/login") {
            val user = call.receive<User>()
            println(user)
            call.respond(User("${user.id}${user.id}", "${user.password}${user.password}"))
        }
    }
}
