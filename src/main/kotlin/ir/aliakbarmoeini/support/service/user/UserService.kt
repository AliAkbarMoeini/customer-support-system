package ir.aliakbarmoeini.support.service.user

import ir.aliakbarmoeini.support.dto.user.RegisterUserDto
import ir.aliakbarmoeini.support.dto.user.UserDetailsDto
import org.springframework.security.core.userdetails.UserDetailsService

interface UserService : UserDetailsService {

    fun save(registerUserDto: RegisterUserDto): UserDetailsDto

    fun getAll(): List<UserDetailsDto>

    fun updateUserRoles(userId: Long, roles: Array<String>)

    fun getById(userId: Long): UserDetailsDto

}