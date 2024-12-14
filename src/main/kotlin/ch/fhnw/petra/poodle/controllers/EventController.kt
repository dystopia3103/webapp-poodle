package ch.fhnw.petra.poodle.controllers

import ch.fhnw.petra.poodle.dtos.EventFormModel
import ch.fhnw.petra.poodle.dtos.EventViewModel
import ch.fhnw.petra.poodle.dtos.ParticipationViewModel
import ch.fhnw.petra.poodle.dtos.TimeSlotFormModel
import ch.fhnw.petra.poodle.entities.Event
import ch.fhnw.petra.poodle.entities.EventTimeSlot
import ch.fhnw.petra.poodle.misc.TemporalHelper
import ch.fhnw.petra.poodle.services.EventService
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.server.ResponseStatusException

@Controller
class EventController(private val eventService: EventService, private val objectMapper: ObjectMapper) {

    @GetMapping("/event/{link}")
    fun eventDetail(@PathVariable link: String, model: Model): String {
        val event = try {
            eventService.find(link)
        } catch (e: NoSuchElementException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message)
        }

        val eventViewModel = EventViewModel.fromEvent(event)
        val participationViewModels = event.participations.map { participation ->
            ParticipationViewModel(
                participantName = participation.participantName,
                votes = event.timeSlots.map { timeSlot ->
                    participation.votes.map { vote -> vote.timeSlot?.id }.contains(timeSlot.id)
                }
            )
        }

        model.addAttribute("event", eventViewModel)
        model.addAttribute("participations", participationViewModels)
        return "event/event_detail"
    }

    @GetMapping("/events/create")
    fun eventCreateForm(model: Model): String {
        val newEvent = EventFormModel()
        model.addAttribute("eventForm", newEvent)
        model.addAttribute("eventId", 0)
        return "event/event_form"
    }

    @GetMapping("/events/update/{id}")
    fun eventUpdateForm(@PathVariable id: Int, model: Model): String {
        val event = eventService.find(id)
        val form = EventFormModel(
            name = event.name,
            description = event.description,
            timeSlots = event.timeSlots.map {
                TimeSlotFormModel(
                    TemporalHelper.dateStringFromInstant(it.start!!),
                    TemporalHelper.timeStringFromInstant(it.start!!),
                    TemporalHelper.timeStringFromInstant(it.end!!)
                )
            }.toMutableList(),
            participantEmails = event.participantEmails
        )

        model.addAttribute("eventForm", form)
        model.addAttribute("eventId", id)
        model.addAttribute("participantEmailsJson", ObjectMapper().writeValueAsString(form.participantEmails))
        model.addAttribute("timeSlotsJson", ObjectMapper().writeValueAsString(form.timeSlots))
        return "event/event_form"
    }

    @PostMapping("/events/save/{id}")
    fun saveEvent(
        @PathVariable id: Int,
        @Valid @ModelAttribute form: EventFormModel,
        bindingResult: BindingResult,
        model: Model,
    ): String {
        if (bindingResult.hasErrors()) {
            model.addAttribute("eventForm", form)
            model.addAttribute("eventId", id)
            model.addAttribute("validationErrors", bindingResult.allErrors)
            model.addAttribute("participantEmailsJson", ObjectMapper().writeValueAsString(form.participantEmails))
            model.addAttribute("timeSlotsJson", ObjectMapper().writeValueAsString(form.timeSlots))
            return "event/event_form"
        }

        val existingEvent = if (id != 0) eventService.find(id) else Event()
        val updatedEvent = existingEvent.copy(
            name = form.name,
            description = form.description,
            participantEmails = form.participantEmails
        )

        updatedEvent.timeSlots.clear()
        form.timeSlots.forEach {
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