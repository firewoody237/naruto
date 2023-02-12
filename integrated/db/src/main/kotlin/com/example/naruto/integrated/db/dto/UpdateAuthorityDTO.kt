package com.example.naruto.integrated.db.dto

import com.example.naruto.integrated.user.Authority

data class UpdateAuthorityDTO(
        val id: Long?,
        val authority: Authority?
)