package ch.fhnw.petra.poodle.controllers

import ch.fhnw.petra.poodle.dtos.formmodels.EventFormModel
import ch.fhnw.petra.poodle.dtos.formmodels.TimeSlotFormModel
import ch.fhnw.petra.poodle.dtos.viewmodels.EventTimeSlotViewModel
import ch.fhnw.petra.poodle.dtos.viewmodels.EventViewModel
import ch.fhnw.petra.poodle.dtos.viewmodels.ParticipationViewModel
import ch.fhnw.petra.poodle.entities.Event
import ch.fhnw.petra.poodle.entities.EventTimeSlot
import ch.fhnw.petra.poodle.misc.TemporalHelper
import ch.fhnw.petra.poodle.misc.UrlHelper
import ch.fhnw.petra.poodle.services.EmailService
import ch.fhnw.petra.poodle.services.EventService
import ch.fhnw.petra.poodle.services.MeetingService
import ch.fhnw.petra.poodle.services.ParticipationService
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException


@Controller
class EventController(
    private val eventService: EventService,
    private val participationService: ParticipationService,
    private val meetingService: MeetingService,
    private val urlHelper: UrlHelper,
    private val emailService: EmailService,
) {

    @GetMapping("/event/{link}")
    fun eventDetail(@PathVariable link: String, model: Model): String {
        return eventDetailCommon(link, model, "event/event_detail")
    }

    @GetMapping("/event/admin/{link}")
    fun eventDetailAdmin(@PathVariable link: String, model: Model): String {
        val participations = participationService.findByEventLink(link)
        val allVotes = participations.flatMap { p -> p.votes }
        val timeSlotCounts = allVotes.groupBy { it.timeSlot }.mapValues { it.value.size }
        val bestSlot = timeSlotCounts.maxByOrNull { it.value }?.key

        val viewModel = if (bestSlot == null) null else EventTimeSlotViewModel.fromEventTimeSlot(bestSlot)
        model.addAttribute("bestTimeSlot", viewModel)

        model.addAttribute("shareLink", urlHelper.createUrl("/participate/${link}"))

        return eventDetailCommon(link, model, "event/event_detail_admin")
    }

    private fun eventDetailCommon(link: String, model: Model, viewName: String): String {
        if (meetingService.exists(link)) {
            return "redirect:/meeting/$link"
        }

        val event = eventService.find(link)

        val eventViewModel = EventViewModel.fromEvent(event)
        val participationViewModels = event.participations.map { participation ->
            ParticipationViewModel(
                participantName = participation.participantName,
                votes = event.timeSlots.map { timeSlot ->
                    participation.votes.map { vote -> vote.timeSlot?.id }.contains(timeSlot.id)
                }
            )
        }

        model.addAttribute("eventId", event.id)
        model.addAttribute("event", eventViewModel)
        model.addAttribute("participations", participationViewModels)
        return viewName
    }

    @GetMapping("/event/create")
    fun eventCreateForm(model: Model): String {
        val newEvent = EventFormModel()
        model.addAttribute("eventForm", newEvent)
        model.addAttribute("eventId", 0)
        return "event/event_form"
    }

    @GetMapping("/event/update/{id}")
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

    @PostMapping("/event/save/{id}")
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
            name = form.name.trim(),
            description = form.description.trim(),
            participantEmails = form.participantEmails.map { it.trim().ifBlank { null } }.filterNotNull()
                .toMutableList() // set participant emails to new list
        )

        updatedEvent.timeSlots.clear() // make sure to remove old time slots
        form.timeSlots.forEach {
            val timeSlot = EventTimeSlot(
                start = TemporalHelper.instantFromDateTimeString(it.date, it.startTime),
                end = TemporalHelper.instantFromDateTimeString(it.date, it.endTime),
                event = updatedEvent,
            )
            updatedEvent.timeSlots.add(timeSlot)
        }

        eventService.save(updatedEvent)

        emailService.sendInvitation(updatedEvent)

        return "redirect:/event/admin/" + updatedEvent.link
    }

    @GetMapping("/event/send-reminders/{id}")
    fun sendReminders(@PathVariable id: Int): String {
        val event = eventService.find(id)
        emailService.sendReminder(event)
        return "redirect:/event/admin/" + event.link
    }

    @PostMapping("/event/delete/{id}")
    fun deleteEvent(
        @PathVariable id: Int,
    ): String {
        if (!eventService.exists(id)) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Event does not exist")
        }
        eventService.delete(id)
        return "redirect:/"
    }

    @ExceptionHandler(NoSuchElementException::class)
    @ResponseStatus(NOT_FOUND)
    fun notFound(model: Model): String {
        return "404"
    }

}