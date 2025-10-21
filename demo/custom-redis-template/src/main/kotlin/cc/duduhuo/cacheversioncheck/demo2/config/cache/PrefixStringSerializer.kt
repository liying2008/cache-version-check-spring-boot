package cc.duduhuo.cacheversioncheck.demo2.config.cache

import cc.duduhuo.cacheversioncheck.demo2.profile.Demo2Profile
import org.springframework.data.redis.serializer.RedisSerializer
import org.springframework.stereotype.Component

@Component
class PrefixStringSerializer(private val demo2Profile: Demo2Profile) : RedisSerializer<String> {
    override fun serialize(t: String?): ByteArray? {
        // key 前缀
        val keyPrefix = demo2Profile.redis.keyPrefix
        val key = if (keyPrefix.isEmpty()) {
            "" + t
        } else {
            "$keyPrefix::$t"
        }
        return key.toByteArray(Charsets.UTF_8)
    }

    override fun deserialize(bytes: ByteArray?): String? {
        if (bytes == null) {
            return null
        }
        val key = bytes.toString(Charsets.UTF_8)
        val prefixLength = demo2Profile.redis.keyPrefix.length
        val keyWithoutPrefix = if (prefixLength > 0) {
            key.substring(prefixLength + 2)
        } else {
            key
        }
        return keyWithoutPrefix
    }
}
