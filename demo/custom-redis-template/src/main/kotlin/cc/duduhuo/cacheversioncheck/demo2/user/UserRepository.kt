package cc.duduhuo.cacheversioncheck.demo2.user

import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<UserEntity, Long> {
    fun findByName(name: String): UserEntity?
    fun existsByName(name: String): Boolean
}
