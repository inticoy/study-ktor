package com.practice

import com.auth0.jwt.algorithms.Algorithm

const val HOST = "0.0.0.0"
const val PORT = 8082

const val JWT_ISSUER = "http://$HOST:$PORT"
const val JWT_SECRET = "RSAECRET"
val JWT_ALGORITHM = Algorithm.HMAC256(JWT_SECRET)
