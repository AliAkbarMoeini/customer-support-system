package ir.aliakbarmoeini.support.repository.user

import ir.aliakbarmoeini.support.domain.user.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
    fun findByUsername(username: String): User?
}