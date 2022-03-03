package com.practice.plugins

import com.practice.routing.v1Routing
import io.ktor.client.utils.EmptyContent.status
import io.ktor.http.*
import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.util.pipeline.*

fun Application.configureRouting() {
    routing {
        route("v1") {
            v1Routing()
        }
    }
}

suspend inline fun <reified T : Any> PipelineContext<*, ApplicationCall>.receive(): T? {
    return try {
        call.receive()
    } catch (e: BadRequestException) {
        call.respondText(text = "${e.message}", status = HttpStatusCode.BadRequest)
        null
    }
}

fun PipelineContext<*, ApplicationCall>.getUserId(): Int =
    call.principal<JWTPrincipal>()!!.payload.getClaim("userId").asInt()