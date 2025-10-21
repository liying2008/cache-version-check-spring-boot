package org.example.demo1.cacheversion

import cc.duduhuo.cacheversioncheck.CacheVersion
import cc.duduhuo.cacheversioncheck.VersionCheckStrategy
import org.example.demo1.book.BookConst

@CacheVersion(
    version = "v1",
    strategy = VersionCheckStrategy.CUSTOM,
    keyPatterns = ["${BookConst.CACHE_KEY_PREFIX}::*"],
    customHandler = "outdatedCacheHandler"
)
class BookCacheVersion
