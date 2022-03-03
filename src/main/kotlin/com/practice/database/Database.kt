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
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.configureDatabase() {
    Database.connect(
        url = "jdbc:mysql://$AWS_RDS_BASEURL:$AWS_RDS_PORT/unithon",
        driver = "com.mysql.cj.jdbc.Driver",
        user = AWS_RDS_USER,
        password = AWS_RDS_PASSWORD
    )
    transaction {
        SchemaUtils.createMissingTablesAndColumns(Users)
    }

}

object Database {
    fun findUserByKakaoId(kakaoId: Long) = transaction {
        User.find {
            Users.kakaoId eq kakaoId
        }.firstOrNull()
    }

    fun createUserByNickname(kakaoId: Long, nickname: String) = transaction {
        User.new {
            this.kakaoId = kakaoId
            this.nickname = nickname
        }
    }

    fun changeUserNickname(userId: Int, nickname: String) = transaction {
        User.find {
            Users.id eq userId
        }.firstOrNull()?.nickname = nickname
    }
}