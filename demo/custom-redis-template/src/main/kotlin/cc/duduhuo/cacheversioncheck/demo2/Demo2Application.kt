package cc.duduhuo.cacheversioncheck.demo2

import cc.duduhuo.cacheversioncheck.CacheVersionScan
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@CacheVersionScan
class Demo2Application

fun main(args: Array<String>) {
    runApplication<Demo2Application>(*args)
}
