package ir.aliakbarmoeini.support.dto.user

import ir.aliakbarmoeini.support.domain.user.Role
import ir.aliakbarmoeini.support.domain.user.User
import org.springframework.security.crypto.password.PasswordEncoder
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class RegisterUserDto(
    @field:NotBlank
    val username: String,
    @field:NotBlank
    val firstName: String,
    @field:NotBlank
    val lastName: String,
    @field:NotBlank
    val email: String,
    @field:Size(min = 6, max = 32)
    val password: String
) {

    fun toUser(roles: MutableSet<Role>, encoder: PasswordEncoder) =
        User(-1, username, firstName, lastName, email, encoder.encode(password))
            .also { it.roles = roles }

}
