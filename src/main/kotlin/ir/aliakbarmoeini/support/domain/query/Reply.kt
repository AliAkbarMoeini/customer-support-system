package ir.aliakbarmoeini.support.domain.query

import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime
import javax.persistence.*

@Entity
data class Reply(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,
    var message: String,
    var isFromSupporter: Boolean,
    @ManyToOne(fetch = FetchType.LAZY)
    var query: Query? = null,
    @CreationTimestamp
    var dateTime: LocalDateTime? = null,
)
