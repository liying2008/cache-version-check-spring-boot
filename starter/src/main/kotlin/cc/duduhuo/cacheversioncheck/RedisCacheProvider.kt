/*
 * Copyright 2025 Li Ying.
 * Licensed under the MIT License.
 */

package cc.duduhuo.cacheversioncheck

import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.ApplicationContext
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component


@ConditionalOnClass(RedisTemplate::class)
@ConditionalOnProperty(
    prefix = "cache.version-check",
    name = ["preferred-cache-type"],
    havingValue = "redis",
    matchIfMissing = false
)
@Component
class RedisCacheProvider(properties: CacheVersionCheckProperties, applicationContext: ApplicationContext) : CacheProvider {
    private val logger = LoggerFactory.getLogger(RedisCacheProvider::class.java)

    private val redisTemplate: RedisTemplate<String, *>

    init {
        val redisTemplateBeanName = properties.redis.redisTemplate
        logger.info("[CacheVersionCheck] using RedisTemplate bean: {}", redisTemplateBeanName)
        @Suppress("UNCHECKED_CAST")
        redisTemplate = applicationContext.getBean(redisTemplateBeanName, RedisTemplate::class.java) as RedisTemplate<String, *>
    }

    companion object {
        /**
         * 存放全局缓存版本的 Redis Hash Key
         * hashKey：类全限定名；hashValue：版本字符串
         */
        const val HASH_KEY = "version:for-checking"
    }

    override fun getCacheVersion(triggerClassName: String): String? {
        val version = redisTemplate.opsForHash<String, Any?>().get(HASH_KEY, triggerClassName) ?: return null
        return version.toString()
    }

    override fun setCacheVersion(triggerClassName: String, version: String) {
        redisTemplate.opsForHash<String, Any?>().put(HASH_KEY, triggerClassName, version)
    }

    override fun deleteByPatterns(keyPatterns: Array<String>, ignoreKeyPatterns: Array<String>) {
        val keys = mutableSetOf<String>()
        for (keyPattern in keyPatterns) {
            keys.addAll(redisTemplate.keys(keyPattern))
        }
        val ignoreKeys = mutableSetOf<String>()
        for (ignoreKeyPattern in ignoreKeyPatterns) {
            ignoreKeys.addAll(redisTemplate.keys(ignoreKeyPattern))
        }
        if (ignoreKeys.isEmpty()) {
            if (keys.isNotEmpty()) {
                logger.info("[CacheVersionCheck] delete {} keys", keys.size)
                redisTemplate.delete(keys)
            } else {
                // nothing to delete
                logger.info("[CacheVersionCheck] delete 0 keys")
            }
        } else {
            val keysToDelete = keys - ignoreKeys
            if (keysToDelete.isNotEmpty()) {
                logger.info("[CacheVersionCheck] delete {} keys", keysToDelete.size)
                redisTemplate.delete(keysToDelete)
            } else {
                // nothing to delete
                logger.info("[CacheVersionCheck] delete 0 keys")
            }
        }
    }

    override fun getCacheType(): CacheType {
        return CacheType.REDIS
    }
}
