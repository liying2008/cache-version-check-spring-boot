/*
 * Copyright 2025 Li Ying.
 * Licensed under the MIT License.
 */

package cc.duduhuo.cacheversioncheck

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.util.*


@Component
class CacheProviderFactory(private val properties: CacheVersionCheckProperties, cacheProviders: List<CacheProvider>) {
    private val logger = LoggerFactory.getLogger(CacheProviderFactory::class.java)

    private val supportedCacheProviders = mutableMapOf<CacheType, CacheProvider>()

    init {
        cacheProviders.forEach { provider ->
            if (provider.isSupported()) {
                logger.info("[CacheVersionCheck] cache provider {} is supported", provider.getCacheType())
                supportedCacheProviders[provider.getCacheType()] = provider
            }
        }
    }

    /**
     * 获取当前激活的缓存服务
     */
    fun getActiveCacheProvider(): CacheProvider {
        // 优先使用配置指定的缓存类型
        if (properties.preferredCacheType != null) {
            val provider = supportedCacheProviders[properties.preferredCacheType]
                ?: throw IllegalArgumentException("Preferred cache type ${properties.preferredCacheType} is not supported")
            return provider
        }

        // 如果没有配置指定的缓存类型，则默认使用 Redis
        return supportedCacheProviders[CacheType.REDIS]!!
    }

    /**
     * 获取所有可用的缓存服务
     */
    fun getAvailableCacheProviders(): Map<CacheType, CacheProvider> {
        return Collections.unmodifiableMap(supportedCacheProviders)
    }
}
