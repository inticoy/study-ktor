package com.practice.plugins

import io.ktor.server.auth.*
import io.ktor.util.*
import io.ktor.server.auth.jwt.*
import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.practice.JWT_ALGORITHM
import com.practice.JWT_ISSUER
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.request.*

fun Application.configureSecurity() {

    authentication {
        jwt {
            verifier(
                JWT
                    .require(JWT_ALGORITHM)
                    .withIssuer(JWT_ISSUER)
                    .build()
            )
            validate { credential ->
                if (credential.payload.expiresAt.time > System.currentTimeMillis() && credential.issuer?.contains(
                        JWT_ISSUER
                    ) == true
                ) JWTPrincipal(credential.payload) else null
            }
        }
    }

}
