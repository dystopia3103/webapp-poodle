package ch.fhnw.petra.poodle.controllers

import ch.fhnw.petra.poodle.dtos.formmodels.ParticipationFormModel
import ch.fhnw.petra.poodle.dtos.viewmodels.EventViewModel
import ch.fhnw.petra.poodle.entities.Event
import ch.fhnw.petra.poodle.entities.EventTimeSlot
import ch.fhnw.petra.poodle.entities.Participation
import ch.fhnw.petra.poodle.entities.Vote
import ch.fhnw.petra.poodle.services.EventService
import ch.fhnw.petra.poodle.services.EventTimeSlotService
import ch.fhnw.petra.poodle.services.MeetingService
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
    private val eventTimeSlotService: EventTimeSlotService,
    private val meetingService: MeetingService,
) {

    //todo: Global error handling
    //todo: Testing

    @GetMapping("/participate/{eventLink}")
    fun participate(
        @PathVariable eventLink: String,
        model: Model,
    ): String {
        val event = fetchEvent(eventLink)

        val participationForm = ParticipationFormModel(
            eventLink = event.link,
        )

        val viewModel = EventViewModel.fromEvent(event)

        model.addAttribute("participationForm", participationForm)
        model.addAttribute("event", viewModel)

        return "participation/participation_form"
    }

    @PostMapping("/participate")
    fun saveParticipation(
        @Valid @ModelAttribute participationForm: ParticipationFormModel,
        bindingResult: BindingResult,
        model: Model,
    ): String {
        if (meetingService.exists(participationForm.eventLink)) {
            return "redirect:/meeting/${participationForm.eventLink}"
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("participationForm", participationForm)
            model.addAttribute("validationErrors", bindingResult.allErrors)
            return "participation/participation_form"
        }

        val event = fetchEvent(participationForm.eventLink)

        val participation = Participation(
            participantName = participationForm.participantName,
            event = event,
        )
        participationForm.participations.forEach {
            if (it.timeSlotId != -1) {
                val timeSlot = fetchTimeSlot(it.timeSlotId)
                val vote = Vote(timeSlot = timeSlot, participation = participation)
                participation.votes.add(vote)
            }
        }
        event.participations.add(participation)
        eventService.save(event)
        return "redirect:/event/" + event.link
    }

    private fun fetchEvent(eventLink: String): Event {
        return try {
            eventService.find(eventLink)
        } catch (e: NoSuchElementException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message)
        }
    }

    private fun fetchTimeSlot(timeSlotId: Int): EventTimeSlot {
        return try {
            eventTimeSlotService.find(timeSlotId)
        } catch (e: NoSuchElementException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message)
        }
    }
}