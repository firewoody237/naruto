package com.example.naruto.api.controller

data class UserVO(
        val id: Long,
        val name: String,
        val nickname: String,
        val email: String,
        val grade: String,
        val point: Long,
)