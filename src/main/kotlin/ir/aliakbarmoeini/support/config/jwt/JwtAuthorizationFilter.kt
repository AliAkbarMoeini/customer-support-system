package ir.aliakbarmoeini.support.config.jwt

import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.SignatureException
import ir.aliakbarmoeini.support.facade.JwtFacade
import ir.aliakbarmoeini.support.service.user.UserService
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtAuthorizationFilter(val jwtFacade: JwtFacade, val userService: UserService) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authorizationToken: String? = request.getHeader("Authorization")

        var username: String? = null
        val token: String
        // JWT Token is in the form "Bearer token". Remove Bearer word and get only the Token
        if (authorizationToken != null && authorizationToken.startsWith("Bearer ")) {
            token = authorizationToken.substring(7)
            try {
                username = jwtFacade.getUsernameFromToken(token)
            } catch (ex: MalformedJwtException) {
                println("MalformedJwtException | SignatureException")
            } catch (ex: SignatureException) {
                println("MalformedJwtException | SignatureException")
            } catch (ex: ExpiredJwtException) {
                println("ExpiredJwtException")
            }
        } else {
            filterChain.doFilter(request, response)
            return
        }

        // Once we get the token validate it.
        if (username != null && SecurityContextHolder.getContext().authentication == null) {
            val user = userService.loadUserByUsername(username)

            // if token is valid configure Spring Security to manually set authentication
            if (jwtFacade.validateToken(token, user)) {
                val upat = UsernamePasswordAuthenticationToken(
                    user, null, user.authorities
                )
                upat.details = WebAuthenticationDetailsSource().buildDetails(request)
                // After setting the Authentication in the context, we specify
                // that the current user is authenticated. So it passes the
                // Spring Security Configurations successfully.
                SecurityContextHolder.getContext().authentication = upat
            }
        }
        filterChain.doFilter(request, response)
    }
}