package ir.aliakbarmoeini.support.dto.query

import ir.aliakbarmoeini.support.domain.query.Reply
import java.time.LocalDateTime

data class ReplyDetailsDto(
    val id: Long,
    val message: String,
    var isSupporterReply: Boolean,
    val creationDateTime: LocalDateTime,
) {
    constructor(reply: Reply) : this(reply.id, reply.message, reply.isFromSupporter, reply.dateTime!!)
}
