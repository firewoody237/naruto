package com.example.naruto.integrated.db.dto

import com.example.naruto.integrated.db.entity.User

data class CreateUserDTO(
        var name: String?,
        var nickname: String?,
        var email: String?,
        var password: String?,
) {
    fun toEntity(): User {
        val aUser = User(
                name = this.name,
                nickname = this.nickname,
                email = this.email,
        )
        aUser.password = this.password
        return aUser
    }
}