package ir.aliakbarmoeini.support.service.query

import ir.aliakbarmoeini.support.dto.query.CreateReplyDto
import ir.aliakbarmoeini.support.dto.query.ReplyDetailsDto

interface ReplyService {

    fun submitReply(userId: Long, queryId: Long, createReplyDto: CreateReplyDto): ReplyDetailsDto

    fun getAll(userId: Long, queryId: Long): List<ReplyDetailsDto>

}