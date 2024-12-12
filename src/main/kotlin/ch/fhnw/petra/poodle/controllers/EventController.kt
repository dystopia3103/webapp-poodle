package ch.fhnw.petra.poodle.controllers

import ch.fhnw.petra.poodle.entities.Event
import ch.fhnw.petra.poodle.services.EventService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping

@Controller
class EventController(private val eventService: EventService) {

    @GetMapping("/events/create")
    fun eventCreateForm(model: Model): String {
        val newEvent = Event(name = "", description = "");
        model.addAttribute("event", newEvent)
        return "event/event_form"
    }

    @GetMapping("/events/update/{id}")
    fun eventUpdateForm(@PathVariable id: Int, model: Model): String {
        val event = eventService.find(id)
        model.addAttribute("event", event)
        return "event_form"
    }

    @PostMapping("/events/save/{id}")
    fun saveEvent(@PathVariable id: Int, @ModelAttribute event: Event): String {
        val existingEvent = if (id != 0) eventService.find(id) else event
        val updatedEvent = existingEvent.copy(name = event.name, description = event.description)
        eventService.save(updatedEvent)
        return "redirect:/" //todo
    }

}