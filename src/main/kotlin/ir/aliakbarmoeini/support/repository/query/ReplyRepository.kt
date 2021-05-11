package ir.aliakbarmoeini.support.repository.query

import ir.aliakbarmoeini.support.domain.query.Reply
import org.springframework.data.jpa.repository.JpaRepository

interface ReplyRepository : JpaRepository<Reply, Long> {

    fun findAllByQueryUserIdAndQueryId(user_id: Long, query_id: Long): List<Reply>
}