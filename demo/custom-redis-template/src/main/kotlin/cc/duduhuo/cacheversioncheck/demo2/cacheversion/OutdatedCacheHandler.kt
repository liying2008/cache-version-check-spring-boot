package cc.duduhuo.cacheversioncheck.demo2.cacheversion

import cc.duduhuo.cacheversioncheck.CacheProvider
import cc.duduhuo.cacheversioncheck.VersionCheckState
import cc.duduhuo.cacheversioncheck.VersionMismatchHandler
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component("outdatedCacheHandler")
class OutdatedCacheHandler() : VersionMismatchHandler {
    private val logger = LoggerFactory.getLogger(OutdatedCacheHandler::class.java)

    override fun handleMismatch(cacheProvider: CacheProvider, state: VersionCheckState) {
        val triggerClassName = state.trigger
        val expectedVersion = state.expectedVersion
        val actualVersion = state.actualVersion
        val keyPatterns = state.keyPatterns
        val ignoreKeyPatterns = state.ignoreKeyPatterns
        logger.info(
            "handle outdated cache data: [{}] expected version is {}, but existing version is {}, patterns={}",
            triggerClassName, expectedVersion, actualVersion, keyPatterns
        )
        cacheProvider.deleteByPatterns(keyPatterns, ignoreKeyPatterns)
        cacheProvider.setCacheVersion(triggerClassName.name, expectedVersion)
    }
}
