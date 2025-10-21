/*
 * Copyright 2025 Li Ying.
 * Licensed under the MIT License.
 */

package cc.duduhuo.cacheversioncheck

import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
class CacheVersionAutoChecker(private val cacheVersionChecker: CacheVersionChecker) : ApplicationRunner {

    override fun run(args: ApplicationArguments?) {
        cacheVersionChecker.execute()
    }
}
