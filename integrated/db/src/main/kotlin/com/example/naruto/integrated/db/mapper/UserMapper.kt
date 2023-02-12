package com.example.naruto.integrated.db.mapper

import com.example.naruto.integrated.db.entity.User
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select

@Mapper
interface UserMapper {

    @Select(
            """
        select *
        from user
        """
    )
    fun getUsers(): List<User>
}