package com.example.toy

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringBootToyApplication

fun main(args: Array<String>) {
	runApplication<SpringBootToyApplication>(*args)
}
