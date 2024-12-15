package ch.fhnw.petra.poodle.controllers

import ch.fhnw.petra.poodle.entities.Event
import ch.fhnw.petra.poodle.entities.EventTimeSlot
import ch.fhnw.petra.poodle.services.EventService
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseStatus
import java.time.Instant

@Controller
class HomeController(private val eventService: EventService) {

    @GetMapping("/")
    fun home(model: Model): String {
        model.addAttribute("events", eventService.findAll())
        return "index"
    }

    @GetMapping("/about")
    fun about() : String {
        return "about"
    }

    @ExceptionHandler(NoSuchElementException::class)
    @ResponseStatus(NOT_FOUND)
    fun notFound(model: Model): String {
        return "404"
    }

}