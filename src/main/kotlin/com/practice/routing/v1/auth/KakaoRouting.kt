package com.practice.routing.v1.auth

import com.practice.database.Database
import com.practice.dto.Token
import com.practice.network.body.KakaoTokenInfoBody
import com.practice.network.client
import com.practice.plugins.makeServerAccessToken
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.kakaoRouting() {
    post("login") {
        val token = call.receive<Token>()
        val kakaoTokenInfo: KakaoTokenInfoBody = client.get("https://kapi.kakao.com/v1/user/access_token_info") {
            headers {
                append(HttpHeaders.Authorization, "Bearer ${token.kakaoAccessToken}")
            }
        }.body()
        kakaoTokenInfo.msg?.let {
            return@post call.respond(status = HttpStatusCode.BadRequest, message = it)
        }
        val user = Database.findUserByKakaoId(kakaoTokenInfo.id!!)
            ?: return@post call.respond(status = HttpStatusCode.NotFound, message = "Not found")
        call.respond(Token(serverAccessToken = makeServerAccessToken(user.id.value)))
    }
}
