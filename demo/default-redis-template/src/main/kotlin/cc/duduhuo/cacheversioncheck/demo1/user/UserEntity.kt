package cc.duduhuo.cacheversioncheck.demo1.user

import cc.duduhuo.cacheversioncheck.CacheVersion
import cc.duduhuo.cacheversioncheck.VersionCheckStrategy
import cc.duduhuo.cacheversioncheck.demo1.user.UserConst.CACHE_KEY_PREFIX
import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Index
import javax.persistence.Table

/**
 * 可以在实体类上使用，也可以在单独的类中使用。
 */
@CacheVersion(version = "1", keyPatterns = ["${CACHE_KEY_PREFIX}:*"], strategy = VersionCheckStrategy.AUTO_DELETE)
@Entity
@Table(
    name = "t_demo1_user", indexes = [
        Index(name = "idx_name", columnList = "name", unique = true)]
)
class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long? = null,

    @Column(name = "name", nullable = false)
    var name: String? = null,

    @Column(name = "create_at", nullable = false)
    var createAt: String? = null
) : Serializable
