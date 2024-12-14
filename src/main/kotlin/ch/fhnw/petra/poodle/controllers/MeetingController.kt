package ch.fhnw.petra.poodle.controllers

import ch.fhnw.petra.poodle.entities.Event
import ch.fhnw.petra.poodle.entities.Meeting
import ch.fhnw.petra.poodle.services.EventService
import ch.fhnw.petra.poodle.services.EventTimeSlotService
import ch.fhnw.petra.poodle.services.MeetingService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.server.ResponseStatusException

@Controller
class MeetingController(
    private val meetingService: MeetingService,
    private val eventService: EventService,
    private val eventTimeSlotService: EventTimeSlotService,
) {

    @GetMapping("meeting/{link}")
    fun meetingDetail(
        @PathVariable link: String,
        model: Model,
    ): String {
        val meeting = try {
            meetingService.find(link)
        } catch (e: NoSuchElementException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message)
        }

        model.addAttribute("meeting", meeting)
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
        return "redirect:/meeting/" + event.link
    }

    private fun fetchEvent(eventLink: String): Event {
        return try {
            eventService.find(eventLink)
        } catch (e: NoSuchElementException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message)
        }
    }

}