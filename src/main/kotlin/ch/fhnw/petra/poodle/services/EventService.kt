package ch.fhnw.petra.poodle.services

import ch.fhnw.petra.poodle.EventRepository
import ch.fhnw.petra.poodle.entities.Event
import org.springframework.stereotype.Service

@Service
class EventService(private val eventRepo: EventRepository) {

    fun findAll(): List<Event> {
        return eventRepo.findAll()
    }

    fun add(event: Event) {
        eventRepo.save(event)
    }

}