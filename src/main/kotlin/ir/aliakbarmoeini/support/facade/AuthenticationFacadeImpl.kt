package ir.aliakbarmoeini.support.facade

import ir.aliakbarmoeini.support.domain.user.User
import ir.aliakbarmoeini.support.enumeration.Role
import ir.aliakbarmoeini.support.exception.AuthorizationException
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class AuthenticationFacadeImpl : AuthenticationFacade {

    override fun getAuthentication(): Authentication = SecurityContextHolder.getContext().authentication

    override fun getUser(): User = SecurityContextHolder.getContext().authentication.principal as User

    override fun isAuthorized(userId: Long) {
        if (getUser().id != userId && getUserRole() != Role.ADMIN && getUserRole() != Role.SUPPORTER)
            throw AuthorizationException("you don't have required authority")
    }

    override fun getUserRole(): Role {
        val roles = getUser().roles
        return when {
            roles.any { it.name == "ROLE_ADMIN" } -> Role.ADMIN
            roles.any { it.name == "ROLE_SUPPORTER" } -> Role.SUPPORTER
            else -> Role.USER
        }
    }
}