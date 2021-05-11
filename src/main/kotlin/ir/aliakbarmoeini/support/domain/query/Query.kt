package ir.aliakbarmoeini.support.domain.query

import ir.aliakbarmoeini.support.domain.user.User
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime
import javax.persistence.*

@Entity
data class Query(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,
    var title: String,
    var isOpen: Boolean,
    @CreationTimestamp
    var creationDateTime: LocalDateTime? = null,
) {
    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true)
    @JoinColumn(name = "query_id")
    var replies: MutableList<Reply> = mutableListOf()

    @ManyToOne(fetch = FetchType.LAZY)
    var user: User? = null

    fun addReply(reply: Reply) {
        replies.add(reply)
        reply.query = this
    }

    fun removeReply(reply: Reply) {
        replies.remove(reply)
        reply.query = null
    }
}
