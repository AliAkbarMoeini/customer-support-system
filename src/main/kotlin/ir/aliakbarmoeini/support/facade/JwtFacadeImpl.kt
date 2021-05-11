package ir.aliakbarmoeini.support.facade

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.impl.DefaultClock
import ir.aliakbarmoeini.support.domain.user.User
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.util.*
import java.util.function.Function

@Component
class JwtFacadeImpl : JwtFacade {

    private val clock = DefaultClock.INSTANCE

    @Value("\${support.jwt.signing-key}")
    lateinit var secret: String

    @Value("\${support.jwt.token-expiration}")
    private val expiration: Long? = null

    override fun getUsernameFromToken(token: String): String = getClaimFromToken(token, Claims::getSubject)

    fun getIssuedAtDateFromToken(token: String): Date = getClaimFromToken(token, Claims::getIssuedAt)

    fun getExpirationDateFromToken(token: String): Date = getClaimFromToken(token, Claims::getExpiration)

    fun <T> getClaimFromToken(token: String, claimsResolver: Function<Claims, T>): T {
        val claims = getAllClaimsFromToken(token)
        return claimsResolver.apply(claims)
    }

    private fun getAllClaimsFromToken(token: String): Claims {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).body
    }

    private fun isTokenExpired(token: String): Boolean {
        val expiration = getExpirationDateFromToken(token)
        return expiration.before(clock.now())
    }

    private fun ignoreTokenExpiration(token: String) = false

    override fun generateToken(userDetails: UserDetails): String {
        val claims = mutableMapOf<String, Any>()
        return doGenerateToken(claims, userDetails.username)
    }

    private fun doGenerateToken(claims: MutableMap<String, Any>, subject: String): String {
        val createdDate = clock.now()
        val expirationDate = calculateExpirationDate(createdDate)
        return Jwts.builder()
            .setClaims(claims)
            .setSubject(subject)
            .setIssuedAt(createdDate)
            .setExpiration(expirationDate)
            .signWith(SignatureAlgorithm.HS512, secret)
            .compact()
    }

    override fun canTokenBeRefreshed(token: String) = !isTokenExpired(token) || ignoreTokenExpiration(token)


    override fun refreshToken(token: String): String {
        val createdDate = clock.now()
        val expirationDate = calculateExpirationDate(createdDate)
        val claims = getAllClaimsFromToken(token)
        claims.issuedAt = createdDate
        claims.expiration = expirationDate
        return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, secret).compact()
    }

    override fun validateToken(token: String, userDetails: UserDetails): Boolean {
        val user: User = userDetails as User
        val username = getUsernameFromToken(token)
        return username == user.username && !isTokenExpired(token)
    }

    private fun calculateExpirationDate(createdDate: Date) = Date(createdDate.time + expiration!! * 1000)


}