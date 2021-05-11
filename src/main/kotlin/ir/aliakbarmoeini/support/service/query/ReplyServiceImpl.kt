package ir.aliakbarmoeini.support.service.query

import ir.aliakbarmoeini.support.domain.query.Reply
import ir.aliakbarmoeini.support.dto.query.CreateReplyDto
import ir.aliakbarmoeini.support.dto.query.ReplyDetailsDto
import ir.aliakbarmoeini.support.enumeration.Role
import ir.aliakbarmoeini.support.exception.NotFoundException
import ir.aliakbarmoeini.support.facade.AuthenticationFacade
import ir.aliakbarmoeini.support.repository.query.QueryRepository
import ir.aliakbarmoeini.support.repository.query.ReplyRepository
import org.springframework.stereotype.Service

@Service
class ReplyServiceImpl(
    val replyRepo: ReplyRepository,
    val queryRepo: QueryRepository,
    val authenticationFacade: AuthenticationFacade
) : ReplyService {

    override fun submitReply(userId: Long, queryId: Long, createReplyDto: CreateReplyDto): ReplyDetailsDto {
        authenticationFacade.isAuthorized(userId)
        val role = authenticationFacade.getUserRole()
        val query = queryRepo.findByUserIdAndId(userId, queryId) ?: throw NotFoundException()
        val isSupporterReply = when (role) {
            Role.ADMIN, Role.SUPPORTER -> true
            Role.USER -> false
        }
        var reply = Reply(-1, createReplyDto.message, isSupporterReply, query)
        reply = replyRepo.save(reply)
        return ReplyDetailsDto(reply)
    }

    override fun getAll(userId: Long, queryId: Long): List<ReplyDetailsDto> {
        authenticationFacade.isAuthorized(userId)
        return replyRepo.findAllByQueryUserIdAndQueryId(userId, queryId).map { ReplyDetailsDto(it) }
    }


}