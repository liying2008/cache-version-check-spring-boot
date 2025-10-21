package cc.duduhuo.cacheversioncheck.demo3

import org.example.demo1.IncludeExampleDemo1Application
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@IncludeExampleDemo1Application
class Demo3Application

fun main(args: Array<String>) {
    runApplication<Demo3Application>(*args)
}
