package com.mksherbini.cars

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CarScrapperApplication

fun main(args: Array<String>) {
	runApplication<CarScrapperApplication>(*args).close()
}
