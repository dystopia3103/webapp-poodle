package ch.fhnw.petra.poodle.services

import ch.fhnw.petra.poodle.EventTimeSlotRepository

class EventTimeSlotService(private val eventTimeSlotRepo: EventTimeSlotRepository) {

    fun existsById(id: Int): Boolean {
        return eventTimeSlotRepo.existsById(id)
    }

}