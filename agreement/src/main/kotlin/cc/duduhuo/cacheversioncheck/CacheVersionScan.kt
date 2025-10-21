/*
 * Copyright 2025 Li Ying.
 * Licensed under the MIT License.
 */

package cc.duduhuo.cacheversioncheck

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class CacheVersionScan(
    /**
     * 指定需要扫描的包路径。如果不指定，则使用当前注解的组件的包路径
     */
    val basePackages: Array<String> = [],
)
