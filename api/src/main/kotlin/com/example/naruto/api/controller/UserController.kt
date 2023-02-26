package com.example.naruto.api.controller

import com.example.naruto.integrated.common.resultcode.ResultCode
import com.example.naruto.integrated.common.resultcode.ResultCodeException
import com.example.naruto.integrated.db.dto.*
import com.example.naruto.integrated.db.service.UserService
import com.example.naruto.integrated.webservice.api.ApiRequestMapping
import org.apache.logging.log4j.Level
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

    @ApiRequestMapping("/users", method = [RequestMethod.GET])
    fun getUser(@RequestBody getUserDTO: GetUserDTO): UserVO {
        log.debug("call getUser : getUserDTO = '$getUserDTO'")
        val user =
            when {
                getUserDTO.id != null -> {
                    userService.getUserById(getUserDTO.id!!)
                }

                getUserDTO.name?.isNotEmpty() == true -> {
                    userService.getUserByName(getUserDTO.name!!)
                }

                else -> {
                    throw ResultCodeException(
                        resultCode = ResultCode.ERROR_PARAMETER_NOT_EXISTS,
                        loglevel = Level.WARN,
                        message = "id && name all null or empty"
                    )
                }
            }
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
    fun updateUser(@RequestBody updateUserDTO: UpdateUserDTO): UserVO {
        log.debug("call updateUser : updateUserDTO = '$updateUserDTO'")
        val user = userService.updateUser(updateUserDTO)
        return UserVO(
            id = user.id,
            name = user.name!!,
            nickname = user.nickname!!,
            email = user.email!!,
            grade = user.grade.toString(),
            point = user.point
        )
    }

    @ApiRequestMapping("/users", method = [RequestMethod.DELETE])
    fun deleteUser(@RequestBody deleteUserDTO: DeleteUserDTO) {
        log.debug("call deleteUser, deleteUserDTO = '$deleteUserDTO'")
        return userService.deleteUser(deleteUserDTO)
    }

    @ApiRequestMapping("/users/point", method = [RequestMethod.POST])
    fun updateUserPoint(@RequestBody updatePointDTO: UpdatePointDTO): UserVO {
        log.debug("call updateUserPoint, updatePointDTO = '${updatePointDTO}'")
        val user = userService.updatePoint(updatePointDTO)
        return UserVO(
            id = user.id,
            name = user.name!!,
            nickname = user.nickname!!,
            email = user.email!!,
            grade = user.grade.toString(),
            point = user.point
        )
    }

    @ApiRequestMapping("/users/grade", method = [RequestMethod.POST])
    fun updateUserGrade(@RequestBody updateGradeDTO: UpdateGradeDTO): UserVO {
        log.debug("call updateGradeDTO, updateGradeDTO = '${updateGradeDTO}'")
        val user = userService.updateGrade(updateGradeDTO)
        return UserVO(
            id = user.id,
            name = user.name!!,
            nickname = user.nickname!!,
            email = user.email!!,
            grade = user.grade.toString(),
            point = user.point
        )
    }

    @ApiRequestMapping("/users/authority", method = [RequestMethod.POST])
    fun updateUserAuthority(@RequestBody updateAuthorityDTO: UpdateAuthorityDTO): UserVO {
        log.debug("call updateUserAuthority, updateAuthorityDTO = '${updateAuthorityDTO}")
        val user = userService.updateAuthority(updateAuthorityDTO)
        return UserVO(
            id = user.id,
            name = user.name!!,
            nickname = user.nickname!!,
            email = user.email!!,
            grade = user.grade.toString(),
            point = user.point
        )
    }
}