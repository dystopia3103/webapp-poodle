package ch.fhnw.petra.poodle.services

import ch.fhnw.petra.poodle.EventTimeSlotRepository
import ch.fhnw.petra.poodle.entities.EventTimeSlot
import org.springframework.stereotype.Service

@Service
class EventTimeSlotService(private val eventTimeSlotRepo: EventTimeSlotRepository) {

    fun find(id: Int): EventTimeSlot {
        return eventTimeSlotRepo.findById(id).orElseThrow()
    }

}