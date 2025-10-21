package cc.duduhuo.cacheversioncheck.demo1

import cc.duduhuo.cacheversioncheck.CacheVersionScan
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@EnableCaching
@SpringBootApplication
@ComponentScan(basePackages = ["org.example.demo1"])
@EntityScan(basePackages = ["org.example.demo1"])
@EnableJpaRepositories(basePackages = ["org.example.demo1"])
@CacheVersionScan(basePackages = ["cc.duduhuo.cacheversioncheck.demo1", "org.example.demo1"])
class Demo1Application

fun main(args: Array<String>) {
    runApplication<Demo1Application>(*args)
}
