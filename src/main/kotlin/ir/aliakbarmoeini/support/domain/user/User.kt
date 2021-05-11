package ir.aliakbarmoeini.support.domain.user

import ir.aliakbarmoeini.support.domain.query.Query
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import javax.persistence.*

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,
    private var username: String,
    var firstName: String,
    var lastName: String,
    var email: String,
    private var password: String
) : UserDetails {

    @ManyToMany(fetch = FetchType.EAGER)
    var roles: MutableSet<Role> = mutableSetOf()

    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true)
    @JoinColumn(name = "user_id")
    var queries: MutableList<Query> = mutableListOf()

    fun addQuery(query: Query) {
        queries.add(query)
        query.user = this
    }

    fun removeQuery(query: Query) {
        queries.remove(query)
        query.user = null
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> =
        roles.map { SimpleGrantedAuthority(it.name) }.toMutableSet()

    override fun getPassword() = password

    override fun getUsername() = username

    override fun isAccountNonExpired() = true

    override fun isAccountNonLocked() = true

    override fun isCredentialsNonExpired() = true

    override fun isEnabled() = true
}
