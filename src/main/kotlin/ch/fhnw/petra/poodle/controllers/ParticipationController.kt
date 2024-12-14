package ch.fhnw.petra.poodle.controllers

import ch.fhnw.petra.poodle.dtos.EventFormModel
import ch.fhnw.petra.poodle.dtos.EventTimeSlotViewModel
import ch.fhnw.petra.poodle.dtos.ParticipationFormModel
import ch.fhnw.petra.poodle.entities.Participation
import ch.fhnw.petra.poodle.misc.TemporalHelper
import ch.fhnw.petra.poodle.services.EventService
import ch.fhnw.petra.poodle.services.ParticipationService
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
class ParticipationController(
    private val eventService: EventService,
    private val participationService: ParticipationService,
) {

    @GetMapping("/participate/{eventId}")
    fun participate(
        @PathVariable eventId: Int,
        model: Model,
    ): String {
        val event = try {
            eventService.find(eventId)
        } catch (e: NoSuchElementException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message)
        }

        val participationForm = ParticipationFormModel(
            eventLink = event.link,
        )
        model.addAttribute("participationForm", participationForm)
        model.addAttribute("eventName", event.name)
        model.addAttribute("eventDescription", event.description)
        model.addAttribute("eventTimeSlots", event.timeSlots.map {
            EventTimeSlotViewModel(
                from = TemporalHelper.dateStringFromInstant(it.start!!),
                to = TemporalHelper.dateStringFromInstant(it.end!!),
            )
        })

        return "participation/participation_form"
    }

    @PostMapping("/participate")
    fun saveParticipation(
        @Valid @ModelAttribute participationForm: ParticipationFormModel,
        bindingResult: BindingResult,
        model: Model,
    ): String {
        if (bindingResult.hasErrors()) {
            model.addAttribute("participationForm", participationForm)
            model.addAttribute("validationErrors", bindingResult.allErrors)
            return "participation/participation_form"
        }
        return "redirect" //todo: Redirect to event detail
    }
}