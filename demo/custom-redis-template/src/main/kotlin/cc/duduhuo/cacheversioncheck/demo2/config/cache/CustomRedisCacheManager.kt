package cc.duduhuo.cacheversioncheck.demo2.config.cache

import org.springframework.data.redis.cache.RedisCache
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.cache.RedisCacheWriter
import java.time.Duration

class CustomRedisCacheManager(cacheWriter: RedisCacheWriter, defaultCacheConfiguration: RedisCacheConfiguration) :
    RedisCacheManager(cacheWriter, defaultCacheConfiguration) {
    override fun createRedisCache(
        name: String,
        cacheConfig: RedisCacheConfiguration?
    ): RedisCache {
        val hashIndex = name.lastIndexOf('#')
        return if (hashIndex == -1) {
            super.createRedisCache(name, cacheConfig)
        } else {
            val cacheName = name.take(hashIndex)
            val expireTime = Duration.ofSeconds(name.substring(hashIndex + 1).toLong())
            val newCacheConfig = cacheConfig?.entryTtl(expireTime)
            super.createRedisCache(cacheName, newCacheConfig)
        }
    }
}
