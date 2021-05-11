package ir.aliakbarmoeini.support.dto.user

import javax.validation.constraints.NotBlank

data class AuthenticationDto(
    @field:NotBlank
    var username: String,
    @field:NotBlank
    var password: String
)
