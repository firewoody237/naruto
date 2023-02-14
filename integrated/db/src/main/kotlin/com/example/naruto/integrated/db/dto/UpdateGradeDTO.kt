package com.example.naruto.integrated.db.dto

import com.example.naruto.integrated.db.user.Grade

data class UpdateGradeDTO(
    val id: Long?,
    val grade: Grade?,
)