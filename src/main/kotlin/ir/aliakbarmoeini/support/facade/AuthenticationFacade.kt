package ir.aliakbarmoeini.support.facade

import ir.aliakbarmoeini.support.domain.user.User
import ir.aliakbarmoeini.support.enumeration.Role
import org.springframework.security.core.Authentication

interface AuthenticationFacade {

    fun getAuthentication(): Authentication

    fun getUser(): User

    fun isAuthorized(userId: Long)

    fun getUserRole(): Role
}