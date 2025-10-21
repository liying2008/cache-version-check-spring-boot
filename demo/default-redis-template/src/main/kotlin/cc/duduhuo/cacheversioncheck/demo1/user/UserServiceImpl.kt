package cc.duduhuo.cacheversioncheck.demo1.user

import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class UserServiceImpl(private val userRepository: UserRepository) : UserService {
    @Cacheable(value = [UserConst.CACHE_KEY_PREFIX], key = "#id")
    override fun findById(id: Long): UserEntity? {
        return userRepository.findByIdOrNull(id)
    }

    override fun findByName(name: String): UserEntity? {
        return userRepository.findByName(name)
    }

    override fun findAll(): List<UserEntity> {
        return userRepository.findAll()
    }

    @CachePut(value = [UserConst.CACHE_KEY_PREFIX], key = "#result.id")
    override fun add(userEntity: UserEntity): UserEntity {
        val name = userEntity.name
        if (name.isNullOrEmpty()) {
            throw IllegalArgumentException("User name is required")
        }
        if (userRepository.existsByName(name)) {
            throw IllegalArgumentException("User name already exists")
        }
        userEntity.id = null
        userEntity.createAt = LocalDateTime.now().toString()
        return userRepository.save(userEntity)
    }

    @CachePut(value = [UserConst.CACHE_KEY_PREFIX], key = "#result.id")
    override fun updateById(
        id: Long,
        userEntity: UserEntity
    ): UserEntity {
        val user = findById(id) ?: throw IllegalArgumentException("User not found")
        val name = userEntity.name
        if (name.isNullOrEmpty()) {
            throw IllegalArgumentException("User name is required")
        }
        if (user.name != name) {
            // 更新了 name
            if (userRepository.existsByName(name)) {
                throw IllegalArgumentException("User name already exists")
            }
        }

        user.id = id
        user.name = name
        return userRepository.save(user)
    }

    @CacheEvict(value = [UserConst.CACHE_KEY_PREFIX], key = "#id")
    override fun delete(id: Long) {
        if (!userRepository.existsById(id)) {
            throw IllegalArgumentException("User not found")
        }
        userRepository.deleteById(id)
    }
}
