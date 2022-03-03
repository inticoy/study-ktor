package com.practice.database

import com.practice.AWS_RDS_BASEURL
import com.practice.AWS_RDS_PASSWORD
import com.practice.AWS_RDS_PORT
import com.practice.AWS_RDS_USER
import com.practice.database.entities.User
import com.practice.database.entities.Users
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.configureDatabase() {
    Database.connect(
        "jdbc:mysql://$AWS_RDS_BASEURL:$AWS_RDS_PORT/unithon",
        driver = "com.mysql.cj.jdbc.Driver",
        user = AWS_RDS_USER,
        password = AWS_RDS_PASSWORD
    )
    transaction {
        SchemaUtils.create(Users)
    }

}

object Database {
    fun findUserByKakaoId(kakaoId:Long) = transaction {
        User.find {
            Users.kakaoId eq kakaoId
        }.firstOrNull()
    }
}