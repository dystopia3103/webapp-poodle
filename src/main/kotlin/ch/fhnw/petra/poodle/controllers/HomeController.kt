package ch.fhnw.petra.poodle.controllers

import ch.fhnw.petra.poodle.entities.Event
import ch.fhnw.petra.poodle.entities.EventTimeSlot
import ch.fhnw.petra.poodle.services.EventService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import java.time.Instant

@Controller
class HomeController(private val eventService: EventService) {

    @GetMapping("/")
    fun home(model: Model): String {
        val e = Event(
            link = "test",
            name = "test",
            timeSlot = EventTimeSlot(
                start = Instant.now(),
                end = Instant.now().plusSeconds(10),
            ),
        )
        eventService.add(e)
        model.addAttribute("event", eventService.findAll().first())
        return "index"
    }

}