package ir.aliakbarmoeini.support.dto.user

import ir.aliakbarmoeini.support.domain.user.User
import javax.validation.constraints.NotBlank

data class UserDetailsDto(
    val id: Long,
    @field:NotBlank
    val username: String,
    @field:NotBlank
    val firstName: String,
    @field:NotBlank
    val lastName: String,
    @field:NotBlank
    val email: String,
    val roles: Set<String>
) {
    constructor(user: User) : this(
        user.id,
        user.username,
        user.firstName,
        user.lastName,
        user.email,
        user.roles.map { it.name }.toSet()
    )
}