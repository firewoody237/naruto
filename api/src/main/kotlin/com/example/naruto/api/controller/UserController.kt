package com.example.naruto.api.controller

import com.example.naruto.integrated.db.dto.*
import com.example.naruto.integrated.db.entity.User
import com.example.naruto.integrated.db.service.UserService
import com.example.naruto.integrated.webservice.api.ApiRequestMapping
import org.apache.logging.log4j.LogManager
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1")
class UserController(
    private val userService: UserService,
) {
    companion object {
        private val log = LogManager.getLogger()
    }

//    @ApiRequestMapping("/users", method = [RequestMethod.GET])
//    fun getUsers(@RequestBody getUserDTO: GetUserDTO): Page<UserVO> {
//        log.debug("call getUsers : getUserDTO = '$getUserDTO'")
//        return userService.getUsers(getUserDTO).map { user ->
//            UserVO(
//                id = user.id,
//                name = user.name!!,
//                nickname = user.nickname!!,
//                email = user.email!!,
//                grade = user.grade.toString(),
//                point = user.point
//            )
//        }
//    }

    @ApiRequestMapping("/users/{name}", method = [RequestMethod.GET])
    fun getUser(@PathVariable name: String): UserVO {
        log.debug("call getUser : name = '$name'")
        val user = userService.getUserByName(name)
        return UserVO(
                id = user.id,
                name = user.name!!,
                nickname = user.nickname!!,
                email = user.email!!,
                grade = user.grade.toString(),
                point = user.point
        )
    }

    @ApiRequestMapping("/users", method = [RequestMethod.POST])
    fun createUser(@RequestBody createUserDTO: CreateUserDTO): UserVO {
        log.debug("call createUser : createUserDTO = '$createUserDTO'")
        val user = userService.createUser(createUserDTO)
        return UserVO(
                id = user.id,
                name = user.name!!,
                nickname = user.nickname!!,
                email = user.email!!,
                grade = user.grade.toString(),
                point = user.point
        )
    }

    @ApiRequestMapping("/users", method = [RequestMethod.PUT])
    fun updateUser(@RequestBody updateUserDTO: UpdateUserDTO): User {
        log.debug("call updateUser : updateUserDTO = '$updateUserDTO'")
        return userService.updateUser(updateUserDTO)
    }

    @ApiRequestMapping("/users", method = [RequestMethod.DELETE])
    fun deleteUser(@RequestBody deleteUserDTO: DeleteUserDTO) {
        log.debug("call deleteUser, deleteUserDTO = '$deleteUserDTO'")
        return userService.deleteUser(deleteUserDTO)
    }

    @ApiRequestMapping("/users/point", method = [RequestMethod.POST])
    fun updateUserPoint(@RequestBody updatePointDTO: UpdatePointDTO): User {
        log.debug("call updateUserPoint, updatePointDTO = '${updatePointDTO}'")
        return userService.updatePoint(updatePointDTO)
    }

    @ApiRequestMapping("/users/grade", method = [RequestMethod.POST])
    fun updateUserGrade(@RequestBody updateGradeDTO: UpdateGradeDTO): User {
        log.debug("call updateGradeDTO, updateGradeDTO = '${updateGradeDTO}'")
        return userService.updateGrade(updateGradeDTO)
    }

    @ApiRequestMapping("/users/authority", method = [RequestMethod.POST])
    fun updateUserAuthority(@RequestBody updateAuthorityDTO: UpdateAuthorityDTO): User {
        log.debug("call updateUserAuthority, updateAuthorityDTO = '${updateAuthorityDTO}")
        return userService.updateAuthority(updateAuthorityDTO)
    }
}