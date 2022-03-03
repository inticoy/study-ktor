package com.practice.routing.v1

import com.practice.database.Database
import com.practice.network.body.KakaoTokenInfoBody
import com.practice.network.body.SignUpBody
import com.practice.network.client
import com.practice.plugins.makeServerAccessToken
import com.practice.plugins.receive
import com.practice.routing.v1.auth.kakaoRouting
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.authRouting() {
    route("kakao") {
        kakaoRouting()
    }

    post("signup") {
        val signUpBody = receive<SignUpBody>() ?: return@post
        val kakaoTokenInfo: KakaoTokenInfoBody = client.get("https://kapi.kakao.com/v1/user/access_token_info") {
            headers {
                append(HttpHeaders.Authorization, "Bearer ${signUpBody.kakaoAccessToken}")
            }
        }.body()
        kakaoTokenInfo.msg?.let {
            return@post call.respond(status = HttpStatusCode.BadRequest, message = it)
        }

        // todo : 닉네임 유효성 검사
        var user = Database.createUserByNickname(kakaoTokenInfo.id!!, signUpBody.nickname!!)
        call.respond(SignUpBody(serverAccessToken = makeServerAccessToken(user.id.value)))
    }
}