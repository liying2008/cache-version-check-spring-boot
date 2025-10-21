package cc.duduhuo.cacheversioncheck.demo2.config.cache

import cc.duduhuo.cacheversioncheck.demo2.profile.Demo2Profile
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheWriter
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.StringRedisSerializer
import java.time.Duration

@AutoConfiguration(after = [RedisAutoConfiguration::class])
@EnableConfigurationProperties(value = [Demo2Profile::class])
@EnableCaching
class RedisConfig(private val prefixStringSerializer: PrefixStringSerializer) {
    /**
     * Redis 序列化和反序列化配置
     *
     * @return 自定义的 RedisCacheManager
     */
    @Bean
    fun cacheManager(connectionFactory: RedisConnectionFactory): CacheManager {
        // RedisCacheConfiguration 默认使用 StringRedisSerializer 序列化和反序列化 key
        // 此处应该使用我们自定义的 PrefixStringSerializer 来序列化和反序列化 key
        // 使用 GenericJackson2JsonRedisSerializer 来序列化和反序列化 value
        val valueSerializer = GenericJackson2JsonRedisSerializer()
        val keyPair = RedisSerializationContext.SerializationPair.fromSerializer(prefixStringSerializer)
        val valuePair = RedisSerializationContext.SerializationPair.fromSerializer(valueSerializer)
        val defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig()
            .entryTtl(Duration.ofMillis(-1))
            .serializeKeysWith(keyPair)
            .serializeValuesWith(valuePair)

        val redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(connectionFactory)
        return CustomRedisCacheManager(redisCacheWriter, defaultCacheConfig)
    }

    /**
     * 配置自定义的 RedisTemplate
     *
     * @return 自定义的 RedisTemplate
     */
    @Bean
    fun redisTemplate(connectionFactory: RedisConnectionFactory): RedisTemplate<String, Any> {
        val template = RedisTemplate<String, Any>()
        template.setConnectionFactory(connectionFactory)

        // 使用 GenericJackson2JsonRedisSerializer 来序列化和反序列化 value
        val serializer = GenericJackson2JsonRedisSerializer()
        template.valueSerializer = serializer
        template.hashValueSerializer = serializer
        // 使用自定义的 PrefixStringSerializer 来序列化和反序列化 key
        template.keySerializer = prefixStringSerializer
        template.hashKeySerializer = StringRedisSerializer()
        template.afterPropertiesSet()
        return template
    }

    /**
     * 配置自定义的 StringRedisTemplate
     *
     * @return 自定义的 StringRedisTemplate
     */
    @Bean
    fun stringRedisTemplate(connectionFactory: RedisConnectionFactory): StringRedisTemplate {
        val template = StringRedisTemplate(connectionFactory)
        // 使用自定义的 PrefixStringSerializer 来序列化和反序列化 key
        template.keySerializer = prefixStringSerializer
        template.hashKeySerializer = StringRedisSerializer()
        template.afterPropertiesSet()
        return template
    }
}
