package cc.duduhuo.cacheversioncheck.demo2.cacheversion

import cc.duduhuo.cacheversioncheck.CacheVersion
import cc.duduhuo.cacheversioncheck.VersionCheckStrategy
import cc.duduhuo.cacheversioncheck.demo2.role.RoleConst

/**
 * 也可在单独的类中声明。
 * 仅支持 class，不支持 interface 。
 */
@CacheVersion(
    version = "1",
    strategy = VersionCheckStrategy.CUSTOM,
    keyPatterns = ["${RoleConst.CACHE_KEY_PREFIX}::*"],
    customHandler = "outdatedCacheHandler"
)
class RoleCacheVersion
