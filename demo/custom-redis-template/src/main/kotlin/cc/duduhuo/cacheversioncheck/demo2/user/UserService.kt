package cc.duduhuo.cacheversioncheck.demo2.user

interface UserService {
    fun findById(id: Long): UserEntity?

    fun findByName(name: String): UserEntity?

    fun findAll(): List<UserEntity>

    fun add(userEntity: UserEntity): UserEntity

    fun updateById(id: Long, userEntity: UserEntity): UserEntity

    fun delete(id: Long)
}
