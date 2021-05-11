package ir.aliakbarmoeini.support.service.query

import ir.aliakbarmoeini.support.dto.query.CreateQueryDto
import ir.aliakbarmoeini.support.dto.query.QueryDetailsDto
import ir.aliakbarmoeini.support.dto.query.UpdateQueryDto

interface QueryService {

    fun save(userId: Long, createQueryDto: CreateQueryDto): QueryDetailsDto

    fun get(userId: Long, queryId: Long): QueryDetailsDto

    fun update(queryId: Long, updateQueryDto: UpdateQueryDto): QueryDetailsDto

    fun getAllByUserId(userId: Long): List<QueryDetailsDto>

    fun deleteById(queryId: Long)

    fun getAll(): List<QueryDetailsDto>

}