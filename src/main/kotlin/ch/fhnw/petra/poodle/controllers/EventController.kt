package ch.fhnw.petra.poodle.controllers

import ch.fhnw.petra.poodle.dtos.EventFormModel
import ch.fhnw.petra.poodle.entities.Event
import ch.fhnw.petra.poodle.entities.EventTimeSlot
import ch.fhnw.petra.poodle.misc.TemporalHelper
import ch.fhnw.petra.poodle.services.EventService
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.validation.Valid
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping

@Controller
class EventController(private val eventService: EventService, private val objectMapper: ObjectMapper) {

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
        //todo: Updating time slots?
        return "event/event_form"
    }

    @PostMapping("/events/save/{id}")
    fun saveEvent(
        @PathVariable id: Int,
        @Valid @ModelAttribute eventForm: EventFormModel,
        bindingResult: BindingResult,
        model: Model,
    ): String {

        val existingEvent = if (id != 0) eventService.find(id) else Event()
        val updatedEvent = existingEvent.copy(name = eventForm.name, description = eventForm.description)

        if (bindingResult.hasErrors()) {
            model.addAttribute("event", updatedEvent)
            model.addAttribute("validationErrors", bindingResult.allErrors)
            return "event/event_form"
        }

        updatedEvent.timeSlots.clear()
        eventForm.timeSlots.forEach {
            val timeSlot = EventTimeSlot(
                start = TemporalHelper.instantFromDateTimeString(it.date, it.startTime),
                end = TemporalHelper.instantFromDateTimeString(it.date, it.endTime),
                event = updatedEvent,
            )
            updatedEvent.timeSlots.add(timeSlot)
        }

        eventService.save(updatedEvent)
        return "redirect:/"
    }

}