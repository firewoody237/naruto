package com.example.naruto.integrated.db.dto

data class UpdateUserDTO(
        val id: Long?,
        var name: String?,
        var nickname: String?,
        var email: String?,
        var password: String?,
)