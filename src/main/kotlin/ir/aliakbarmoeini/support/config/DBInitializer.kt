package ir.aliakbarmoeini.support.config

import ir.aliakbarmoeini.support.domain.user.Role
import ir.aliakbarmoeini.support.domain.user.User
import ir.aliakbarmoeini.support.repository.user.RoleRepository
import ir.aliakbarmoeini.support.repository.user.UserRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component

@Component
class DBInitializer(
    val roleRepo: RoleRepository,
    val userRepo: UserRepository,
    val passwordEncoder: BCryptPasswordEncoder
) : CommandLineRunner {

    override fun run(vararg args: String?) {
        roleRepo.findByName("ROLE_ADMIN") ?: roleRepo.save(Role(-1, "ROLE_ADMIN"))
        roleRepo.findByName("ROLE_SUPPORTER") ?: roleRepo.save(Role(-1, "ROLE_SUPPORTER"))
        roleRepo.findByName("ROLE_USER") ?: roleRepo.save(Role(-1, "ROLE_USER"))
        userRepo.findByUsername("aliakbar") ?: userRepo.save(
            User(
                -1,
                "aliakbar",
                "Ali Akbar",
                "Moeini",
                "hi@aliakbarmoeini.ir",
                passwordEncoder.encode("anotherPass")
            ).apply {
                roles = mutableSetOf(roleRepo.findByName("ROLE_ADMIN")!!)
            }
        )
    }

}