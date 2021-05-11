package ir.aliakbarmoeini.support.dto.query

import javax.validation.constraints.NotBlank

data class CreateQueryDto(
    @field:NotBlank
    val title: String,
    @field:NotBlank
    val text: String
)
