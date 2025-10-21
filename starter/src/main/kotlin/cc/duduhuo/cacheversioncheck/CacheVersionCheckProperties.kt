/*
 * Copyright 2025 Li Ying.
 * Licensed under the MIT License.
 */

package cc.duduhuo.cacheversioncheck

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "cache.version-check")
class CacheVersionCheckProperties(
    /**
     * 是否启用 cache version 检查
     */
    var enabled: Boolean = true,
    /**
     * 是否启用自动检查，自动检查将在应用启动时自动检查缓存版本。
     * 如果不启用自动检查，可通过注入 CacheVersionChecker 组件，通过调用 execute() 方法执行检查
     */
    var autoCheck: Boolean = true,
    /**
     * 首选的缓存类型，目前仅支持 redis
     */
    var preferredCacheType: CacheType? = null,
    /**
     * Redis 配置
     */
    var redis: RedisProperties = RedisProperties(),
) {
    class RedisProperties(
        /**
         * 指定 RedisTemplate 的 Bean 名称，用于读取和写入缓存版本，以及删除过时缓存
         */
        var redisTemplate: String = "stringRedisTemplate"
    )
}
