/*
 * Copyright 2025 Li Ying.
 * Licensed under the MIT License.
 */

package cc.duduhuo.cacheversioncheck

interface CacheProvider {
    /**
     * 读取存储在缓存中的版本号
     *
     * @param triggerClassName 触发器全限定类名
     * @return 缓存中的版本号
     */
    fun getCacheVersion(triggerClassName: String): String?

    /**
     * 写入 / 更新缓存中的版本号
     *
     * @param triggerClassName 触发器全限定类名
     * @param version 需要更新的版本号
     */
    fun setCacheVersion(triggerClassName: String, version: String)

    /**
     * 根据 keyPatterns 和 ignoreKeyPatterns 删除缓存数据
     *
     * @param keyPatterns 需要删除的缓存key模式
     * @param ignoreKeyPatterns 从需要删除的缓存中剔除一些不需要删除的缓存
     */
    fun deleteByPatterns(keyPatterns: Array<String>, ignoreKeyPatterns: Array<String>)

    /**
     * 获取缓存类型
     */
    fun getCacheType(): CacheType

    /**
     * 检查是否支持当前环境
     */
    fun isSupported(): Boolean {
        return true
    }
}
