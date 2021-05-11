package ir.aliakbarmoeini.support.repository.user

import ir.aliakbarmoeini.support.domain.user.Role
import org.springframework.data.jpa.repository.JpaRepository

interface RoleRepository : JpaRepository<Role, Int> {

    fun findByName(name: String): Role?
}