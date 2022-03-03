package com.practice.database.entities

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

// table 명명
object Users : IntIdTable() {
    val kakaoId = long("kakaoId").nullable().uniqueIndex()
    val nickname = varchar("nickname", 20)
}

class User(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<User>(Users)

    var kakaoId by Users.kakaoId
    var nickname by Users.nickname
}