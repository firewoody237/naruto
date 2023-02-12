package com.example.naruto.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.ServletComponentScan


@SpringBootApplication(scanBasePackages = ["com.example.naruto"])
@ServletComponentScan(basePackages = ["com.example.naruto"])
class ApiApplication {

}

fun main(args: Array<String>) {
    runApplication<ApiApplication>(*args)
}