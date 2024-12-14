package ch.fhnw.petra.poodle

import ch.fhnw.petra.poodle.entities.Event
import ch.fhnw.petra.poodle.entities.EventTimeSlot
import ch.fhnw.petra.poodle.entities.Participation
import ch.fhnw.petra.poodle.entities.Vote
import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class DataInitializer {

    @Service
    class DataInitializer(private val eventRepository: EventRepository) {

        @PostConstruct
        fun init() {
            val event = Event(
                name = "Spring Fest",
                description = "A spring celebration",
                link = "spring-fest",
                timeSlots = mutableListOf(
                    EventTimeSlot(
                        start = Instant.parse("2023-04-01T10:00:00Z"),
                        end = Instant.parse("2023-04-01T12:00:00Z"),
                    ),
                    EventTimeSlot(
                        start = Instant.parse("2023-04-02T10:00:00Z"),
                        end = Instant.parse("2023-04-02T12:00:00Z"),
                    ),
                    EventTimeSlot(
                        start = Instant.parse("2023-04-03T10:00:00Z"),
                        end = Instant.parse("2023-04-03T12:00:00Z"),
                    ),
                ),
                participantEmails = mutableListOf(
                    "petra@gmail.com",
                    "pascal@gmail.com",
                    "toby@gmail.com"
                ),
            )
            eventRepository.save(event)
        }
    }


}