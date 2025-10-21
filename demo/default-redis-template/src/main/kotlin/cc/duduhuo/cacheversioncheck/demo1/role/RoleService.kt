package cc.duduhuo.cacheversioncheck.demo1.role

interface RoleService {
    fun findById(id: Long): RoleEntity?

    fun findByName(name: String): RoleEntity?

    fun findAll(): List<RoleEntity>

    fun add(roleEntity: RoleEntity): RoleEntity

    fun updateById(id: Long, roleEntity: RoleEntity): RoleEntity

    fun delete(id: Long)
}
