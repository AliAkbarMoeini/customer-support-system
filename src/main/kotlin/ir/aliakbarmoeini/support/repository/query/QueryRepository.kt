package ir.aliakbarmoeini.support.repository.query

import ir.aliakbarmoeini.support.domain.query.Query
import org.springframework.data.jpa.repository.JpaRepository

interface QueryRepository : JpaRepository<Query, Long> {

    fun findByUserIdAndId(user_id: Long, id: Long): Query?

    fun findAllByUserId(user_id: Long): List<Query>
}