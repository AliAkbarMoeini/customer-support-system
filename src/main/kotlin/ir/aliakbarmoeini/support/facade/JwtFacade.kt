package ir.aliakbarmoeini.support.facade

import org.springframework.security.core.userdetails.UserDetails

interface JwtFacade {

    fun getUsernameFromToken(token: String): String

    fun generateToken(userDetails: UserDetails): String

    fun validateToken(token: String, userDetails: UserDetails): Boolean

    fun canTokenBeRefreshed(token: String): Boolean

    fun refreshToken(token: String): String
}