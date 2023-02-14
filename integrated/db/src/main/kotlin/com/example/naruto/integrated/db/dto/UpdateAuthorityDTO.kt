package com.example.naruto.integrated.db.dto

import com.example.naruto.integrated.db.user.Authority

data class UpdateAuthorityDTO(
        val id: Long?,
        val authority: Authority?
)