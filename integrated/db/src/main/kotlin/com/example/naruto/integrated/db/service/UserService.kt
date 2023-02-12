package com.example.naruto.integrated.db.service

import com.example.naruto.integrated.common.ResultCode
import com.example.naruto.integrated.common.ResultCodeException
import com.example.naruto.integrated.db.dto.*
import com.example.naruto.integrated.db.entity.User
import com.example.naruto.integrated.db.mapper.UserMapper
import com.example.naruto.integrated.db.repository.UserRepository
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.LogManager
import org.springframework.stereotype.Service

@Service
class UserService(
        private val userMapper: UserMapper,
        private val userRepository: UserRepository
) {
    companion object {
        private val log = LogManager.getLogger()
    }

//    fun getUsers(getUserDTO: GetUserDTO): Page<User> {
//        log.debug("call getUsers : getUserDTO='$getUserDTO',")
//
//        val id = getUserDTO.id
//        val name = getUserDTO.name
//        val pageRequest = PageRequest.of(getUserDTO.page, getUserDTO.size)
//
//        try {
//            return when {
//                id?.isNotEmpty() == true && name?.isNotEmpty() == true -> {
//                    userRepository.findByIdLikeAndNameContains(id, name, pageRequest)
//                }
//
//                id?.isNotEmpty() == true -> {
//                    userRepository.findByIdContains(id, pageRequest)
//                }
//
//                name?.isNotEmpty() == true -> {
//                    userRepository.findByNameContains(name, pageRequest)
//                }
//
//                else -> {
//                    userRepository.findAll(pageRequest)
//                }
//            }
//        } catch (e: Exception) {
//            log.error("getUsers DB search failed. $getUserDTO", e)
//            throw ResultCodeException(ResultCode.ERROR_DB , loglevel = Level.ERROR)
//        }
//    }

    fun getUserByName(name: String): User {
        log.debug("call getUserByName : name = '$name'")

        val optionalUser = try {
            userRepository.findByName(name)
        } catch (e: Exception) {
            log.error("getUserByName DB search failed. $name", e)
            throw ResultCodeException(
                    resultCode = ResultCode.ERROR_DB,
                    loglevel = Level.ERROR,
                    message = "getUserByName 호출 중 DB오류 발생 : ${e.message}"
            )
        }

        return when (optionalUser.isPresent) {
            true -> optionalUser.get()
            else -> throw ResultCodeException(
                    resultCode = ResultCode.ERROR_USER_NOT_EXISTS,
                    loglevel = Level.WARN,
                    message = "getUserByName : name['$name'] 유저가 존재하지 않습니다."
            )
        }
    }

    fun getUserById(id: Long): User {
        log.debug("call getUserById : id = '$id'")

        val optionalUser = try {
            userRepository.findById(id)
        } catch (e: Exception) {
            log.error("getUser DB search failed. $id", e)
            throw ResultCodeException(
                resultCode = ResultCode.ERROR_DB,
                loglevel = Level.ERROR,
                message = "getUserById 호출 중 DB오류 발생 : ${e.message}"
            )
        }

        return when (optionalUser.isPresent) {
            true -> optionalUser.get()
            else -> throw ResultCodeException(
                resultCode = ResultCode.ERROR_USER_NOT_EXISTS,
                loglevel = Level.WARN,
                message = "getUserById : id['$id'] 유저가 존재하지 않습니다."
            )
        }
    }

    fun isExistByName(name: String): Boolean {
        log.debug("call isEistByName : name = '$name'")

        return try {
            userRepository.existsByName(name)
        } catch (e: Exception) {
            log.error("isExistByName DB search failed. $name", e)
            throw ResultCodeException(
                resultCode = ResultCode.ERROR_DB,
                loglevel = Level.ERROR,
                message = "isExistByName 호출 중 DB오류 발생 : ${e.message}"
            )
        }
    }

    fun isExistById(id: Long): Boolean {
        log.debug("call isExistById : id = '$id'")

        return try {
            userRepository.existsById(id)
        } catch (e: Exception) {
            log.error("isExistByName DB search failed. $id", e)
            throw ResultCodeException(
                resultCode = ResultCode.ERROR_DB,
                loglevel = Level.ERROR,
                message = "isExistByName 호출 중 DB오류 발생 : ${e.message}"
            )
        }
    }

    fun createUser(createUserDTO: CreateUserDTO): User {
        log.debug("call createUser : createUserDTO = '$createUserDTO'")

        if (createUserDTO.name.isNullOrEmpty()) {
            throw ResultCodeException(
                    resultCode = ResultCode.ERROR_PARAMETER_NOT_EXISTS,
                    loglevel = Level.WARN,
                    message = "파라미터에 [name]이 존재하지 않습니다."
            )
        }

        if (createUserDTO.nickname.isNullOrEmpty()) {
            throw ResultCodeException(
                resultCode = ResultCode.ERROR_PARAMETER_NOT_EXISTS,
                loglevel = Level.WARN,
                message = "파라미터에 [nickname]이 존재하지 않습니다."
            )
        }

        if (createUserDTO.password.isNullOrEmpty()) {
            throw ResultCodeException(
                resultCode = ResultCode.ERROR_PARAMETER_NOT_EXISTS,
                loglevel = Level.WARN,
                message = "파라미터에 [password]가 존재하지 않습니다."
            )
        }

        return when (isExistByName(createUserDTO.name!!)) {
            true -> {
                throw ResultCodeException(
                    resultCode = ResultCode.ERROR_USER_ALREADY_EXISTS,
                    loglevel = Level.INFO,
                    message = "'${createUserDTO.name}'은 이미 존재하는 [Name]입니다."
                )
            }
            else -> {
                try {
                    userRepository.save(createUserDTO.toEntity())
                } catch (e: Exception) {
                    log.error("createUser failed. $createUserDTO", e)
                    throw ResultCodeException(
                        resultCode = ResultCode.ERROR_DB,
                        loglevel = Level.ERROR,
                        message = "isExistByName 호출 중 DB오류 발생 : ${e.message}"
                    )
                }
            }
        }
    }

    fun updateUser(updateUserDTO: UpdateUserDTO): User {
        log.debug("call updateUser : updateUserDTO = '$updateUserDTO'")

        if (updateUserDTO.id == null) {
            throw ResultCodeException(
                    resultCode = ResultCode.ERROR_PARAMETER_NOT_EXISTS,
                    loglevel = Level.WARN,
                    message = "파라미터에 [ID]가 존재하지 않습니다."
            )
        }

        if (updateUserDTO.name?.isNotEmpty() == true && updateUserDTO.name?.length!! > 0) {
            if (isExistByName(updateUserDTO.name!!)) {
                throw ResultCodeException(
                        resultCode = ResultCode.ERROR_USER_ALREADY_EXISTS,
                        loglevel = Level.INFO,
                        message = "'${updateUserDTO.name}'은 이미 존재하는 [Name]입니다."
                )
            }
        }

        var isChange = false
        val user = getUserById(updateUserDTO.id)

        if (updateUserDTO.name?.isNotEmpty() == true) {
            user.name = updateUserDTO.name
            isChange = true
        }

        if (updateUserDTO.nickname?.isNotEmpty() == true) {
            user.nickname = updateUserDTO.nickname
            isChange = true
        }

        if (updateUserDTO.email?.isNotEmpty() == true) {
            user.email = updateUserDTO.email
            isChange = true
        }

        if (updateUserDTO.password?.isNotEmpty() == true) {
            user.password = updateUserDTO.password
            isChange = true
        }

        try {
            return when (isChange) {
                true -> userRepository.save(user)

                else -> throw ResultCodeException(
                    resultCode = ResultCode.ERROR_NOTHING_TO_MODIFY,
                    loglevel = Level.INFO
                )
            }
        } catch (e: Exception) {
            throw ResultCodeException(
                resultCode = ResultCode.ERROR_DB,
                loglevel = Level.ERROR,
                message = "updateUser 호출 중 DB오류 발생 : ${e.message}"
            )
        }
    }

    fun deleteUser(deleteUserDTO: DeleteUserDTO) {
        log.debug("call deleteUser : deleteUserDTO = '$deleteUserDTO'")

        if (deleteUserDTO.id == null) {
            throw ResultCodeException(
                    ResultCode.ERROR_PARAMETER_NOT_EXISTS,
                    loglevel = Level.WARN,
                    message = "파라미터에 [ID]가 존재하지 않습니다."
            )
        }

        if (isExistById(deleteUserDTO.id)) {
            try {
                userRepository.deleteById(deleteUserDTO.id)
            } catch (e: Exception) {
                throw ResultCodeException(
                    resultCode = ResultCode.ERROR_DB,
                    loglevel = Level.ERROR,
                    message = "deleteUser 호출 중 DB오류 발생 : ${e.message}"
                )
            }
        }
    }

    fun updatePoint(updatePointDTO: UpdatePointDTO): User {
        log.debug("call updatePoint : updatePointDTO = '$updatePointDTO'")

        if (updatePointDTO.id == null) {
            throw ResultCodeException(
                    ResultCode.ERROR_PARAMETER_NOT_EXISTS,
                    loglevel = Level.WARN,
                    message = "파라미터에 ID가 존재하지 않습니다."
            )
        }

        val user = getUserById(updatePointDTO.id)

        try {
            if (user.point + updatePointDTO.point!! < 0) {
                throw ResultCodeException(
                    resultCode = ResultCode.ERROR_POINT_MINUS,
                    loglevel = Level.WARN
                )
            }
            user.point += updatePointDTO.point
            return userRepository.save(user)
        } catch (e: Exception) {
            log.error("updateUserPoint failed. $updatePointDTO", e)
            throw ResultCodeException(
                resultCode = ResultCode.ERROR_DB,
                loglevel = Level.ERROR,
                message = "deleteUser 호출 중 DB오류 발생 : ${e.message}"
            )
        }
    }

    fun updateGrade(updateGradeDTO: UpdateGradeDTO): User {
        log.debug("call updateGrade : updateGradeDTO = '$updateGradeDTO'")

        if (updateGradeDTO.id == null) {
            throw ResultCodeException(
                    resultCode =  ResultCode.ERROR_PARAMETER_NOT_EXISTS,
                    loglevel = Level.WARN,
                    message = "파라미터에 [ID]가 존재하지 않습니다."
            )
        }

        if (updateGradeDTO.grade == null) {
            throw ResultCodeException(
                resultCode =  ResultCode.ERROR_PARAMETER_NOT_EXISTS,
                loglevel = Level.WARN,
                message = "파라미터에 [GRADE]가 존재하지 않습니다."
            )
        }

        val user = getUserById(updateGradeDTO.id)

        try {
            user.grade = updateGradeDTO.grade
            return userRepository.save(user)
        } catch (e: Exception) {
            throw ResultCodeException(
                resultCode = ResultCode.ERROR_DB,
                loglevel = Level.ERROR,
                message = "updateGrade 호출 중 DB오류 발생 : ${e.message}"
            )
        }
    }

    fun updateAuthority(updateAuthorityDTO: UpdateAuthorityDTO): User {
        log.debug("call updateAuthority : updateAuthorityDTO = '$updateAuthorityDTO'")

        if (updateAuthorityDTO.id == null) {
            throw ResultCodeException(
                    resultCode = ResultCode.ERROR_PARAMETER_NOT_EXISTS,
                    loglevel = Level.WARN,
                    message = "파라미터에 [ID]가 존재하지 않습니다."
            )
        }

        if (updateAuthorityDTO.authority == null) {
            throw ResultCodeException(
                resultCode = ResultCode.ERROR_PARAMETER_NOT_EXISTS,
                loglevel = Level.WARN,
                message = "파라미터에 [AUTHORITY]가 존재하지 않습니다."
            )
        }

        val user = getUserById(updateAuthorityDTO.id)

        try {
            user.authority = updateAuthorityDTO.authority
            return userRepository.save(user)
        } catch (e: Exception) {
            throw ResultCodeException(
                resultCode = ResultCode.ERROR_DB,
                loglevel = Level.ERROR,
                message = "updateAuthority 호출 중 DB오류 발생 : ${e.message}"
            )
        }
    }
}