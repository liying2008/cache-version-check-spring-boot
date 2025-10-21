package org.example.demo1

import cc.duduhuo.cacheversioncheck.CacheVersionScan
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
@CacheVersionScan(basePackages = ["org.example.demo1"])
class ExampleDemo1Application
