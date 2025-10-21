/*
 * Copyright 2025 Li Ying.
 * Licensed under the MIT License.
 */

package cc.duduhuo.cacheversioncheck

enum class VersionCheckStrategy {
    /**
     * 自动删除版本不匹配的缓存
     */
    AUTO_DELETE,

    /**
     * 仅记录日志，不执行删除操作
     */
    LOG_ONLY,

    /**
     * 抛出异常中断应用启动
     */
    THROW_EXCEPTION,

    /**
     * 自定义处理逻辑
     */
    CUSTOM,
}
