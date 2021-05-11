package ir.aliakbarmoeini.support.service.user

import ir.aliakbarmoeini.support.dto.user.RegisterUserDto
import ir.aliakbarmoeini.support.dto.user.UserDetailsDto
import ir.aliakbarmoeini.support.exception.NotFoundException
import ir.aliakbarmoeini.support.exception.ServiceException
import ir.aliakbarmoeini.support.facade.AuthenticationFacade
import ir.aliakbarmoeini.support.repository.user.RoleRepository
import ir.aliakbarmoeini.support.repository.user.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    val userRepo: UserRepository,
    val roleRepo: RoleRepository,
    val authenticationFacade: AuthenticationFacade,
) : UserService {

    @Autowired
    lateinit var passwordEncoder: BCryptPasswordEncoder

    override fun loadUserByUsername(username: String) = userRepo.findByUsername(username)
        ?: throw UsernameNotFoundException("$username not found")

    override fun save(registerUserDto: RegisterUserDto): UserDetailsDto {
        userRepo.findByUsername(registerUserDto.username)?.run {
            throw ServiceException("this username already exists", HttpStatus.BAD_REQUEST)
        }
        var user = registerUserDto.toUser(mutableSetOf(roleRepo.findByName("ROLE_USER")!!), passwordEncoder)
        user = userRepo.save(user)
        return UserDetailsDto(user)
    }

    override fun getAll(): List<UserDetailsDto> {
        return userRepo.findAll().map { UserDetailsDto(it) }.toList()
    }

    override fun updateUserRoles(userId: Long, roles: Array<String>) {
        val user = userRepo.findByIdOrNull(userId) ?: throw NotFoundException()
        user.roles = roles.mapNotNull { roleRepo.findByName(it) }.toMutableSet()
        userRepo.save(user)
    }

    override fun getById(userId: Long): UserDetailsDto {
        authenticationFacade.isAuthorized(userId)
        return UserDetailsDto(userRepo.findById(userId).get())
    }

}