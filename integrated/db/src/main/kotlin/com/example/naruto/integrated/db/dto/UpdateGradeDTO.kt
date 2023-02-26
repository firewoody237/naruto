package com.example.naruto.integrated.db.dto

import com.example.naruto.integrated.db.enum.Grade

data class UpdateGradeDTO(
    val id: Long?,
    val name : String?,
    val grade: Grade?,
)