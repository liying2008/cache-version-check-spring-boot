/*
 * Copyright 2025 Li Ying.
 * Licensed under the MIT License.
 */

package cc.duduhuo.cacheversioncheck

import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component

@Component
class CacheVersionChecker(
    private val cacheProviderFactory: CacheProviderFactory,
    private val applicationContext: ApplicationContext,
    private val properties: CacheVersionCheckProperties,
) {
    private val logger = LoggerFactory.getLogger(CacheVersionChecker::class.java)

    @Throws(CacheVersionMismatchException::class)
    fun execute() {
        if (!properties.enabled) {
            logger.info("[CacheVersionCheck] disabled by property: cache.version-check.enabled=false")
            return
        }

        val triggers = TriggersFinder.findTriggers(applicationContext)
        if (triggers.isEmpty()) {
            logger.info("[CacheVersionCheck] no cache version triggers found")
            return
        }

        val cacheProvider = cacheProviderFactory.getActiveCacheProvider()

        triggers.forEach { trigger ->
            val triggerClassName = trigger.name
            val ann = trigger.getAnnotation(CacheVersion::class.java)
            val expectedVersion = ann.version
            val keyPatterns = ann.keyPatterns
            val strategy = ann.strategy
            val ignoreKeyPatterns = ann.ignoreKeyPatterns
            val handlerBeanName = ann.customHandler
            val actualVersion = cacheProvider.getCacheVersion(triggerClassName)
            val mismatch = (actualVersion == null) || (expectedVersion != actualVersion)
            if (!mismatch) {
                // 缓存版本匹配
                logger.debug("[CacheVersionCheck] check passed for $triggerClassName")
                return@forEach
            }
            // 缓存版本不匹配，按策略处理
            val checkState = VersionCheckState(
                trigger = trigger,
                expectedVersion = expectedVersion,
                actualVersion = actualVersion,
                keyPatterns = keyPatterns,
                strategy = strategy,
                ignoreKeyPatterns = ignoreKeyPatterns,
            )
            when (strategy) {
                VersionCheckStrategy.AUTO_DELETE -> {
                    logger.info(
                        "[CacheVersionCheck] MISMATCH -> AUTO_DELETE. trigger={}, expected={}, existing={}, patterns={}",
                        triggerClassName, expectedVersion, actualVersion, keyPatterns
                    )
                    cacheProvider.deleteByPatterns(keyPatterns, ignoreKeyPatterns)
                    cacheProvider.setCacheVersion(triggerClassName, expectedVersion)
                }

                VersionCheckStrategy.LOG_ONLY -> {
                    logger.info(
                        "[CacheVersionCheck] MISMATCH -> LOG_ONLY. trigger={}, expected={}, existing={}, patterns={}",
                        triggerClassName, expectedVersion, actualVersion, keyPatterns
                    )
                    // 不更新 cache version
                }

                VersionCheckStrategy.THROW_EXCEPTION -> {
                    val msg = "[CacheVersionCheck] cache version mismatch: $triggerClassName expected version is ${expectedVersion}, but existing version is $actualVersion"
                    logger.error(msg)
                    throw CacheVersionMismatchException(checkState)
                }

                VersionCheckStrategy.CUSTOM -> {
                    if (handlerBeanName.isBlank()) {
                        throw CacheVersionMismatchException(message = "Cache version check: Strategy=CUSTOM but no customHandler bean name specified on $triggerClassName")
                    }
                    val handler = applicationContext.getBean(handlerBeanName, VersionMismatchHandler::class.java)
                    handler.handleMismatch(cacheProvider, checkState)
                    // 由自定义处理器决定是否更新缓存的版本号
                }
            }
        }
    }
}
