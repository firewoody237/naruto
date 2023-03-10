package com.example.naruto.integrated.db.service

import com.example.naruto.integrated.common.resultcode.ResultCode
import com.example.naruto.integrated.common.resultcode.ResultCodeException
import com.example.naruto.integrated.db.dto.*
import com.example.naruto.integrated.db.entity.User
import com.example.naruto.integrated.db.enum.Authority
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

//    fun getUserById(getUserDTO: GetUserDTO): User {
//        log.debug("call getUserById : getUserDTO = '$getUserDTO'")
//
//        if (getUserDTO.id == null) {
//            throw ResultCodeException(
//                resultCode = ResultCode.ERROR_PARAMETER_NOT_EXISTS,
//                loglevel = Level.WARN,
//                message = "??????????????? [id]??? ???????????? ????????????."
//            )
//        }
//
//        val optionalUser = try {
//            userRepository.findById(getUserDTO.id)
//        } catch (e: Exception) {
//            log.error("getUserById DB search failed. ${getUserDTO.id}", e)
//            throw ResultCodeException(
//                    resultCode = ResultCode.ERROR_DB,
//                    loglevel = Level.ERROR,
//                    message = "getUserById ?????? ??? DB?????? ?????? : ${e.message}"
//            )
//        }
//
//        return when (optionalUser.isPresent) {
//            true -> optionalUser.get()
//            else -> throw ResultCodeException(
//                    resultCode = ResultCode.ERROR_USER_NOT_EXISTS,
//                    loglevel = Level.WARN,
//                    message = "getUserById : id['${getUserDTO.id}'] ????????? ???????????? ????????????."
//            )
//        }
//    }

    fun getUserById(id: Long?): User {
        log.debug("call getUserById : getUserDTO = '$id'")

        if (id == null) {
            throw ResultCodeException(
                resultCode = ResultCode.ERROR_PARAMETER_NOT_EXISTS,
                loglevel = Level.WARN,
                message = "??????????????? [id]??? ???????????? ????????????."
            )
        }

        val optionalUser = try {
            userRepository.findById(id)
        } catch (e: Exception) {
            log.error("getUserById DB search failed. ${id}", e)
            throw ResultCodeException(
                resultCode = ResultCode.ERROR_DB,
                loglevel = Level.ERROR,
                message = "getUserById ?????? ??? DB?????? ?????? : ${e.message}"
            )
        }

        return when (optionalUser.isPresent) {
            true -> optionalUser.get()
            else -> throw ResultCodeException(
                resultCode = ResultCode.ERROR_USER_NOT_EXISTS,
                loglevel = Level.WARN,
                message = "getUserById : id['${id}'] ????????? ???????????? ????????????."
            )
        }
    }


    fun getUserByName(name: String?): User {
        log.debug("call getUserByName : name = '$name'")

        if (name.isNullOrEmpty()) {
            throw ResultCodeException(
                resultCode = ResultCode.ERROR_PARAMETER_NOT_EXISTS,
                loglevel = Level.WARN,
                message = "??????????????? [id]??? ???????????? ????????????."
            )
        }

        val optionalUser = try {
            userRepository.findByName(name)
        } catch (e: Exception) {
            log.error("getUserByName DB search failed. $name", e)
            throw ResultCodeException(
                resultCode = ResultCode.ERROR_DB,
                loglevel = Level.ERROR,
                message = "getUserByName ?????? ??? DB?????? ?????? : ${e.message}"
            )
        }

        return when (optionalUser.isPresent) {
            true -> optionalUser.get()
            else -> throw ResultCodeException(
                resultCode = ResultCode.ERROR_USER_NOT_EXISTS,
                loglevel = Level.WARN,
                message = "getUserByName : name['$name'] ????????? ???????????? ????????????."
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
                message = "isExistByName ?????? ??? DB?????? ?????? : ${e.message}"
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
                message = "isExistByName ?????? ??? DB?????? ?????? : ${e.message}"
            )
        }
    }

    fun createUser(createUserDTO: CreateUserDTO): User {
        log.debug("call createUser : createUserDTO = '$createUserDTO'")

        if (createUserDTO.name.isNullOrEmpty()) {
            throw ResultCodeException(
                    resultCode = ResultCode.ERROR_PARAMETER_NOT_EXISTS,
                    loglevel = Level.WARN,
                    message = "??????????????? [name]??? ???????????? ????????????."
            )
        }

        if (createUserDTO.nickname.isNullOrEmpty()) {
            throw ResultCodeException(
                resultCode = ResultCode.ERROR_PARAMETER_NOT_EXISTS,
                loglevel = Level.WARN,
                message = "??????????????? [nickname]??? ???????????? ????????????."
            )
        }

        if (createUserDTO.password.isNullOrEmpty()) {
            throw ResultCodeException(
                resultCode = ResultCode.ERROR_PARAMETER_NOT_EXISTS,
                loglevel = Level.WARN,
                message = "??????????????? [password]??? ???????????? ????????????."
            )
        }

        return when (isExistByName(createUserDTO.name!!)) {
            true -> {
                throw ResultCodeException(
                    resultCode = ResultCode.ERROR_USER_ALREADY_EXISTS,
                    loglevel = Level.INFO,
                    message = "'${createUserDTO.name}'??? ?????? ???????????? [Name]?????????."
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
                        message = "createUser ?????? ??? DB?????? ?????? : ${e.message}"
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
                    message = "??????????????? [ID]??? ???????????? ????????????."
            )
        }

        if (updateUserDTO.name?.isNotEmpty() == true && updateUserDTO.name?.length!! > 0) {
            if (isExistByName(updateUserDTO.name!!)) {
                throw ResultCodeException(
                        resultCode = ResultCode.ERROR_USER_ALREADY_EXISTS,
                        loglevel = Level.INFO,
                        message = "'${updateUserDTO.name}'??? ?????? ???????????? [Name]?????????."
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

        return try {
            when (isChange) {
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
                message = "updateUser ?????? ??? DB?????? ?????? : ${e.message}"
            )
        }
    }

    fun deleteUser(deleteUserDTO: DeleteUserDTO) {
        log.debug("call deleteUser : deleteUserDTO = '$deleteUserDTO'")

        if (deleteUserDTO.id == null) {
            throw ResultCodeException(
                    ResultCode.ERROR_PARAMETER_NOT_EXISTS,
                    loglevel = Level.WARN,
                    message = "??????????????? [ID]??? ???????????? ????????????."
            )
        }

        if (isExistById(deleteUserDTO.id)) {
            try {
                userRepository.deleteById(deleteUserDTO.id)
            } catch (e: Exception) {
                throw ResultCodeException(
                    resultCode = ResultCode.ERROR_DB,
                    loglevel = Level.ERROR,
                    message = "deleteUser ?????? ??? DB?????? ?????? : ${e.message}"
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
                    message = "??????????????? ID??? ???????????? ????????????."
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
                message = "deleteUser ?????? ??? DB?????? ?????? : ${e.message}"
            )
        }
    }

    fun updateGrade(updateGradeDTO: UpdateGradeDTO): User {
        log.debug("call updateGrade : updateGradeDTO = '$updateGradeDTO'")

        if (updateGradeDTO.id == null) {
            throw ResultCodeException(
                    resultCode =  ResultCode.ERROR_PARAMETER_NOT_EXISTS,
                    loglevel = Level.WARN,
                    message = "??????????????? [ID]??? ???????????? ????????????."
            )
        }

        if (updateGradeDTO.grade == null) {
            throw ResultCodeException(
                resultCode =  ResultCode.ERROR_PARAMETER_NOT_EXISTS,
                loglevel = Level.WARN,
                message = "??????????????? [GRADE]??? ???????????? ????????????."
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
                message = "updateGrade ?????? ??? DB?????? ?????? : ${e.message}"
            )
        }
    }

    fun updateAuthority(updateAuthorityDTO: UpdateAuthorityDTO): User {
        log.debug("call updateAuthority : updateAuthorityDTO = '$updateAuthorityDTO'")

        if (updateAuthorityDTO.id == null) {
            throw ResultCodeException(
                    resultCode = ResultCode.ERROR_PARAMETER_NOT_EXISTS,
                    loglevel = Level.WARN,
                    message = "??????????????? [ID]??? ???????????? ????????????."
            )
        }

        if (updateAuthorityDTO.authority == null) {
            throw ResultCodeException(
                resultCode = ResultCode.ERROR_PARAMETER_NOT_EXISTS,
                loglevel = Level.WARN,
                message = "??????????????? [AUTHORITY]??? ???????????? ????????????."
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
                message = "updateAuthority ?????? ??? DB?????? ?????? : ${e.message}"
            )
        }
    }
}