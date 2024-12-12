package ch.fhnw.petra.poodle.services

import ch.fhnw.petra.poodle.EventRepository
import ch.fhnw.petra.poodle.entities.Event
import org.springframework.stereotype.Service

@Service
class EventService(private val eventRepo: EventRepository) {

    fun find(id: Int): Event {
        return eventRepo.findById(id).orElseThrow()
    }

    fun findAll(): List<Event> {
        return eventRepo.findAll()
    }

    fun save(event: Event) {
        eventRepo.save(event)
    }

}