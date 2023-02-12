package com.example.naruto.integrated.db.dto

import com.example.naruto.integrated.common.constant.PAGE
import com.example.naruto.integrated.common.constant.SIZE

data class GetUserDTO(
    val id: Long?,
    val name: String?,
    val nickname: String?,
    val size: Int = SIZE,
    val page: Int = PAGE,
)