package ir.aliakbarmoeini.support.service.query

import ir.aliakbarmoeini.support.domain.query.Query
import ir.aliakbarmoeini.support.domain.query.Reply
import ir.aliakbarmoeini.support.dto.query.CreateQueryDto
import ir.aliakbarmoeini.support.dto.query.QueryDetailsDto
import ir.aliakbarmoeini.support.dto.query.UpdateQueryDto
import ir.aliakbarmoeini.support.exception.NotFoundException
import ir.aliakbarmoeini.support.facade.AuthenticationFacade
import ir.aliakbarmoeini.support.repository.query.QueryRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class QueryServiceImpl(
    val queryRepo: QueryRepository,
    val authenticationFacade: AuthenticationFacade
) : QueryService {

    override fun save(userId: Long, createQueryDto: CreateQueryDto): QueryDetailsDto {
        authenticationFacade.isAuthorized(userId)
        val user = authenticationFacade.getUser()
        val reply = Reply(-1, createQueryDto.text, false)
        val query = Query(-1, createQueryDto.title, true).also { it.addReply(reply) }
        query.user = user
        val saved = queryRepo.save(query)
        return QueryDetailsDto(saved)
    }

    override fun get(userId: Long, queryId: Long): QueryDetailsDto {
        authenticationFacade.isAuthorized(userId)
        return queryRepo.findByUserIdAndId(userId, queryId)
            ?.let { QueryDetailsDto(it) } ?: throw NotFoundException()
    }

    override fun update(queryId: Long, updateQueryDto: UpdateQueryDto): QueryDetailsDto {
        val query = queryRepo.findByIdOrNull(queryId) ?: throw NotFoundException()
        updateQueryDto.title?.let { query.title = it }
        updateQueryDto.isOpen?.let { query.isOpen = it }
        val saved = queryRepo.save(query)
        return QueryDetailsDto(saved)
    }

    override fun getAll(): List<QueryDetailsDto> = queryRepo.findAll().map { QueryDetailsDto(it) }

    override fun deleteById(queryId: Long) {
        queryRepo.deleteById(queryId)
    }

    override fun getAllByUserId(userId: Long): List<QueryDetailsDto> {
        authenticationFacade.isAuthorized(userId)
        return queryRepo.findAllByUserId(userId).map { QueryDetailsDto(it) }
    }

}