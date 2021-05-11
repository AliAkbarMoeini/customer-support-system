package ir.aliakbarmoeini.support.dto.query

import ir.aliakbarmoeini.support.domain.query.Query
import java.time.LocalDateTime

data class QueryDetailsDto(
    val id: Long,
    val title: String,
    val isOpen: Boolean,
    val creationDateTime: LocalDateTime
) {
    constructor(query: Query) : this(query.id, query.title, query.isOpen, query.creationDateTime!!)
}