package cc.duduhuo.cacheversioncheck.demo2.profile

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.NestedConfigurationProperty

@ConfigurationProperties(prefix = "demo2", ignoreUnknownFields = false)
class Demo2Profile(
    /** Redis相关配置 */
    @NestedConfigurationProperty
    var redis: RedisProfile = RedisProfile()
) {
    class RedisProfile(
        /** Redis 缓存 key 前缀 */
        var keyPrefix: String = ""
    )
}
