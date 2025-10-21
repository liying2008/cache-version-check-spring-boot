/*
 * Copyright 2025 Li Ying.
 * Licensed under the MIT License.
 */

package cc.duduhuo.cacheversioncheck

enum class CacheType(value: String) {
    REDIS("redis"),
    CAFFEINE("caffeine"),
    EHCACHE("ehcache"),
    HAZELCAST("hazelcast"),
    MEMORY("memory"),
    CUSTOM("custom");
}
