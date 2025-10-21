/*
 * Copyright 2025 Li Ying.
 * Licensed under the MIT License.
 */

package cc.duduhuo.cacheversioncheck

@FunctionalInterface
fun interface VersionMismatchHandler {
    /**
     * 处理版本不匹配的情况
     * @param cacheProvider 缓存处理器提供者
     * @param state 版本检查的状态
     */
    fun handleMismatch(cacheProvider: CacheProvider, state: VersionCheckState)
}
