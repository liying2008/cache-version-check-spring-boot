package cc.duduhuo.cacheversioncheck.demo1.cacheversion

import cc.duduhuo.cacheversioncheck.CacheVersion
import cc.duduhuo.cacheversioncheck.VersionCheckStrategy
import cc.duduhuo.cacheversioncheck.demo1.role.RoleConst

/**
 * 也可在单独的类中声明。
 * 仅支持 class，不支持 interface 。
 */
@CacheVersion(
    version = "1",
    strategy = VersionCheckStrategy.LOG_ONLY,
    keyPatterns = ["${RoleConst.CACHE_KEY_PREFIX}::*"]
)
class RoleCacheVersion
