package ch.fhnw.petra.poodle.controllers

import ch.fhnw.petra.poodle.dtos.viewmodels.EventTimeSlotViewModel
import ch.fhnw.petra.poodle.dtos.viewmodels.MeetingViewModel
import ch.fhnw.petra.poodle.entities.Event
import ch.fhnw.petra.poodle.entities.Meeting
import ch.fhnw.petra.poodle.misc.UrlHelper
import ch.fhnw.petra.poodle.services.EmailService
import ch.fhnw.petra.poodle.services.EventService
import ch.fhnw.petra.poodle.services.EventTimeSlotService
import ch.fhnw.petra.poodle.services.MeetingService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@Controller
class MeetingController(
    private val meetingService: MeetingService,
    private val eventService: EventService,
    private val eventTimeSlotService: EventTimeSlotService,
    private val emailService: EmailService,
    private val urlHelper: UrlHelper,
) {

    @GetMapping("meeting/{link}")
    fun meetingDetail(
        @PathVariable link: String,
        model: Model,
    ): String {
        val meeting = meetingService.find(link)

        val meetingViewModel = MeetingViewModel(
            name = meeting.event!!.name,
            description = meeting.event.description,
            timeSlot = EventTimeSlotViewModel.fromEventTimeSlot(meeting.timeSlot!!),
            participants = meeting.event.participations
                .filter { p -> p.votes.any { v -> v.timeSlot?.id == meeting.timeSlot?.id } }
                .map { p -> p.participantName })

        model.addAttribute("meeting", meetingViewModel)

        return "meeting/meeting_detail"
    }

    @PostMapping("finalize/{link}")
    fun createMeeting(
        @PathVariable link: String,
        @Valid @RequestParam timeSlotId: Int,
        model: Model,
    ): String {
        val event = fetchEvent(link)
        val timeSlot = try {
            eventTimeSlotService.find(timeSlotId)
        } catch (e: NoSuchElementException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message)
        }

        val meeting = Meeting(
            event = event,
            timeSlot = timeSlot,
        )

        meetingService.save(meeting)

        // important to send mail to actual participants
        emailService.sendMeeting(event)

        return "redirect:/meeting/" + event.link
    }

    private fun fetchEvent(eventLink: String): Event {
        return try {
            eventService.find(eventLink)
        } catch (e: NoSuchElementException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message)
        }
    }

    @ExceptionHandler(NoSuchElementException::class)
    @ResponseStatus(NOT_FOUND)
    fun notFound(model: Model): String {
        return "404"
    }

}