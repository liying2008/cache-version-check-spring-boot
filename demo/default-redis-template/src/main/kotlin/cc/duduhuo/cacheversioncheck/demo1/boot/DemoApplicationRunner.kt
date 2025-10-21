package cc.duduhuo.cacheversioncheck.demo1.boot

import org.slf4j.LoggerFactory
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component
import java.util.Arrays

@Component
class DemoApplicationRunner(private val applicationContext: ApplicationContext) : ApplicationRunner {
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
    }
}
