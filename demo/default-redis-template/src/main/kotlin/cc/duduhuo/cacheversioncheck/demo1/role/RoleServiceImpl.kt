package cc.duduhuo.cacheversioncheck.demo1.role

import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class RoleServiceImpl(private val roleRepository: RoleRepository) : RoleService {
    @Cacheable(value = [RoleConst.CACHE_KEY_PREFIX], key = "#id")
    override fun findById(id: Long): RoleEntity? {
        return roleRepository.findByIdOrNull(id)
    }

    override fun findByName(name: String): RoleEntity? {
        return roleRepository.findByName(name)
    }

    override fun findAll(): List<RoleEntity> {
        return roleRepository.findAll()
    }

    @CachePut(value = [RoleConst.CACHE_KEY_PREFIX], key = "#result.id")
    override fun add(roleEntity: RoleEntity): RoleEntity {
        val name = roleEntity.name
        if (name.isNullOrEmpty()) {
            throw IllegalArgumentException("Role name is required")
        }
        if (roleRepository.existsByName(name)) {
            throw IllegalArgumentException("Role name already exists")
        }
        roleEntity.id = null
        return roleRepository.save(roleEntity)
    }

    @CachePut(value = [RoleConst.CACHE_KEY_PREFIX], key = "#result.id")
    override fun updateById(
        id: Long,
        roleEntity: RoleEntity
    ): RoleEntity {
        val role = findById(id) ?: throw IllegalArgumentException("Role not found")
        val name = roleEntity.name
        if (name.isNullOrEmpty()) {
            throw IllegalArgumentException("Role name is required")
        }
        if (role.name != name) {
            // 更新了 name
            if (roleRepository.existsByName(name)) {
                throw IllegalArgumentException("Role name already exists")
            }
        }

        role.id = id
        role.name = name
        role.description = roleEntity.description
        return roleRepository.save(role)
    }

    @CacheEvict(value = [RoleConst.CACHE_KEY_PREFIX], key = "#id")
    override fun delete(id: Long) {
        if (!roleRepository.existsById(id)) {
            throw IllegalArgumentException("Role not found")
        }
        roleRepository.deleteById(id)
    }

}
