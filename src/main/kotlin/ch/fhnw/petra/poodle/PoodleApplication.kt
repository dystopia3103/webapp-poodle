package ch.fhnw.petra.poodle

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PoodleApplication

fun main(args: Array<String>) {
	runApplication<PoodleApplication>(*args)
}