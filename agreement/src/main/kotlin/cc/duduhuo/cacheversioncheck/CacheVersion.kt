/*
 * Copyright 2025 Li Ying.
 * Licensed under the MIT License.
 */

package cc.duduhuo.cacheversioncheck

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class CacheVersion(
    /**
     * Cache 版本号
     */
    val version: String,
    /**
     * 关联的 Redis key 模式，支持通配符，如：user:cache:*
     */
    val keyPatterns: Array<String> = [],
    /**
     * 版本检查策略，默认为自动删除
     */
    val strategy: VersionCheckStrategy = VersionCheckStrategy.AUTO_DELETE,
    /**
     * 忽略的 Redis key 模式，支持通配符，如：user:cache:robot:*
     */
    val ignoreKeyPatterns: Array<String> = [],

    /**
     * 自定义处理器 Bean 名称，当策略为 CUSTOM 时使用
     */
    val customHandler: String = "",
)
