package com.dgswcns

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@ConfigurationPropertiesScan
@SpringBootApplication
class   LessflowApplication

fun main(args: Array<String>) {
    runApplication<LessflowApplication>(*args)
}
