package ch.fhnw.petra.poodle.controllers

import ch.fhnw.petra.poodle.services.EmailService
import ch.fhnw.petra.poodle.services.EventService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class HomeController(private val eventService: EventService, private val emailService: EmailService) {

    @GetMapping("/")
    fun home(model: Model): String {
        model.addAttribute("events", eventService.findAll())
        return "index"
    }

    @GetMapping("/about")
    fun about(): String {
        return "about"
    }

}