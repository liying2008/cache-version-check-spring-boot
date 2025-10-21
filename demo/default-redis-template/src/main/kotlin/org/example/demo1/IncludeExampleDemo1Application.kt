package org.example.demo1

import org.springframework.context.annotation.Import

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Import(ExampleDemo1Application::class)
annotation class IncludeExampleDemo1Application
