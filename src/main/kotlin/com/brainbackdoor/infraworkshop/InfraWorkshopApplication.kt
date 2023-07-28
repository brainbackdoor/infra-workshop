package com.brainbackdoor.infraworkshop

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class InfraWorkshopApplication

fun main(args: Array<String>) {
	runApplication<InfraWorkshopApplication>(*args)
}
