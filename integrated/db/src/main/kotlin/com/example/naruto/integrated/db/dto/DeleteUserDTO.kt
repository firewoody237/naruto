package com.example.naruto.integrated.db.dto

data class DeleteUserDTO(
        val id: Long?
) {
    constructor(): this(id = 0L)
}