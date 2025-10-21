/*
 * Copyright 2025 Li Ying.
 * Licensed under the MIT License.
 */

package cc.duduhuo.cacheversioncheck

import org.slf4j.LoggerFactory
import org.springframework.aop.framework.AopProxyUtils
import org.springframework.aop.support.AopUtils
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider
import org.springframework.core.type.filter.AnnotationTypeFilter
import org.springframework.util.ClassUtils


object TriggersFinder {
    private val logger = LoggerFactory.getLogger(TriggersFinder::class.java)

    /**
     * 扫描全部 trigger 类
     */
    fun findTriggers(applicationContext: ApplicationContext): MutableSet<Class<*>> {
        val packages = mutableSetOf<String>()
        val beans = applicationContext.getBeansWithAnnotation(CacheVersionScan::class.java)
        beans.forEach { (beanName, bean) ->
            val beanClass = if (AopUtils.isAopProxy(bean)) {
                AopProxyUtils.ultimateTargetClass(bean)
            } else {
                ClassUtils.getUserClass(bean)
            }

            val cacheVersionScanAnno = beanClass.getAnnotation(CacheVersionScan::class.java)
            if (cacheVersionScanAnno.basePackages.isEmpty()) {
                packages.add(beanClass.`package`.name)
            } else {
                packages.addAll(cacheVersionScanAnno.basePackages)
            }
        }

        val scanner = ClassPathScanningCandidateComponentProvider(false)
        scanner.addIncludeFilter(AnnotationTypeFilter(CacheVersion::class.java))

        val result = mutableSetOf<Class<*>>()
        val cl = Thread.currentThread().getContextClassLoader()

        logger.info("[CacheVersionCheck] found {} package(s) to scan", packages.size)
        for (base in packages) {
            for (bd in scanner.findCandidateComponents(base)) {
                try {
                    val clazz = Class.forName(bd.beanClassName, false, cl)
                    logger.info("[CacheVersionCheck] found trigger: {}", clazz.name)
                    result.add(clazz)
                } catch (e: ClassNotFoundException) {
                    logger.error("[CacheVersionCheck] failed to load class: " + bd.beanClassName, e)
                }
            }
        }
        return result
    }
}
