package com.example.naruto.integrated.db.repository

import com.example.naruto.integrated.db.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository: JpaRepository<User, Long?> {
    fun findByName(name: String): Optional<User>
    fun findByIdAndName(id: Long, name: String): Optional<User>
    fun existsByName(name: String): Boolean
}