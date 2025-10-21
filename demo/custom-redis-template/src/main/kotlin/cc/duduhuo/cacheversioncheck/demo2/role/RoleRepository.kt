package cc.duduhuo.cacheversioncheck.demo2.role

import org.springframework.data.jpa.repository.JpaRepository

interface RoleRepository : JpaRepository<RoleEntity, Long> {
    fun findByName(name: String): RoleEntity?
    fun existsByName(name: String): Boolean
}
