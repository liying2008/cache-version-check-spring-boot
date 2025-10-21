/*
 * Copyright 2025 Li Ying.
 * Licensed under the MIT License.
 */

package cc.duduhuo.cacheversioncheck

import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
@EnableConfigurationProperties(CacheVersionCheckProperties::class)
@ConditionalOnProperty(
    prefix = "cache.version-check",
    name = ["enabled"],
    havingValue = "true",
    matchIfMissing = true
)
class CacheVersionCheckAutoConfiguration(private val properties: CacheVersionCheckProperties, private val applicationContext: ApplicationContext) {
    @Bean
    @ConditionalOnMissingBean
    fun redisCacheProvider(): RedisCacheProvider {
        return RedisCacheProvider(properties, applicationContext)
    }

    @Bean
    @ConditionalOnMissingBean
    fun cacheProviderFactory(
        properties: CacheVersionCheckProperties,
        cacheProviders: List<CacheProvider>,
    ): CacheProviderFactory {
        return CacheProviderFactory(properties, cacheProviders)
    }

    @Bean
    @ConditionalOnMissingBean
    fun cacheVersionChecker(
        cacheProviderFactory: CacheProviderFactory,
        applicationContext: ApplicationContext,
        properties: CacheVersionCheckProperties
    ): CacheVersionChecker {
        return CacheVersionChecker(cacheProviderFactory, applicationContext, properties)
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(
        prefix = "cache.version-check",
        name = ["auto-check"],
        havingValue = "true",
        matchIfMissing = true
    )
    fun cacheVersionAutoChecker(cacheVersionChecker: CacheVersionChecker): CacheVersionAutoChecker {
        return CacheVersionAutoChecker(cacheVersionChecker)
    }

    @Bean
    @ConditionalOnMissingBean(name = ["defaultVersionMismatchHandler"])
    fun defaultVersionMismatchHandler(): VersionMismatchHandler {
        return VersionMismatchHandler { cacheProvider: CacheProvider, state: VersionCheckState? ->
            LoggerFactory.getLogger(VersionMismatchHandler::class.java)
                .warn("[CacheVersionCheck] Using default handler for cache version mismatch: {}", state)
        }
    }
}
