/*
 * Copyright 2025 Li Ying.
 * Licensed under the MIT License.
 */

package cc.duduhuo.cacheversioncheck

data class VersionCheckState(
    /**
     * cache version check trigger
     */
    val trigger: Class<*>,
    /**
     * trigger 中指定的版本号
     */
    val expectedVersion: String,
    /**
     * 缓存中的版本号
     */
    val actualVersion: String?,
    /**
     * 关联的缓存 key patterns
     */
    val keyPatterns: Array<String>,
    /**
     * 需要忽略的缓存 key patterns
     */
    val ignoreKeyPatterns: Array<String>,
    /**
     * 版本不匹配时的处理策略
     */
    val strategy: VersionCheckStrategy,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as VersionCheckState

        if (trigger != other.trigger) return false
        if (expectedVersion != other.expectedVersion) return false
        if (actualVersion != other.actualVersion) return false
        if (!keyPatterns.contentEquals(other.keyPatterns)) return false
        if (!ignoreKeyPatterns.contentEquals(other.ignoreKeyPatterns)) return false
        if (strategy != other.strategy) return false

        return true
    }

    override fun hashCode(): Int {
        var result = trigger.hashCode()
        result = 31 * result + expectedVersion.hashCode()
        result = 31 * result + (actualVersion?.hashCode() ?: 0)
        result = 31 * result + keyPatterns.contentHashCode()
        result = 31 * result + ignoreKeyPatterns.contentHashCode()
        result = 31 * result + strategy.hashCode()
        return result
    }
}
