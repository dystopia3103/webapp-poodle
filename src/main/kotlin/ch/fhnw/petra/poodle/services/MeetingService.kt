package ch.fhnw.petra.poodle.services

import ch.fhnw.petra.poodle.MeetingRepository
import ch.fhnw.petra.poodle.entities.Meeting
import org.springframework.stereotype.Service

@Service
class MeetingService(private val meetingRepo: MeetingRepository) {

    fun find(link: String): Meeting {
        return meetingRepo.findByEventLink(link).orElseThrow()
    }

    fun exists(link: String): Boolean {
        return meetingRepo.existsByEventLink(link)
    }

    fun save(meeting: Meeting) {
        meetingRepo.save(meeting)
    }

}