package cc.duduhuo.cacheversioncheck.demo2.boot

import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class DemoCommandLineRunner : CommandLineRunner {
    private val logger = LoggerFactory.getLogger(DemoCommandLineRunner::class.java)

    override fun run(vararg args: String?) {
        logger.info("DemoCommandLineRunner started")
    }
}
