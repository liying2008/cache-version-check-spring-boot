package cc.duduhuo.cacheversioncheck.demo2.boot

import cc.duduhuo.cacheversioncheck.CacheVersionChecker
import org.slf4j.LoggerFactory
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component
import java.util.Arrays

@Component
class DemoApplicationRunner(
    private val applicationContext: ApplicationContext,
    private val cacheVersionChecker: CacheVersionChecker
) : ApplicationRunner {
    private val logger = LoggerFactory.getLogger(DemoApplicationRunner::class.java)

    override fun run(args: ApplicationArguments?) {
        logger.info("DemoApplicationRunner started")
        val names = applicationContext.beanDefinitionNames
        Arrays.sort(names)
        names.forEach {
            val type = applicationContext.getType(it)
            if (type!!.name.startsWith("cc.duduhuo.cacheversioncheck")) {
                logger.info("[DEMO] $it of type :: $type")
            }
        }
        logger.info("[DEMO] cacheVersionChecker.execute()")
        cacheVersionChecker.execute()
    }
}
