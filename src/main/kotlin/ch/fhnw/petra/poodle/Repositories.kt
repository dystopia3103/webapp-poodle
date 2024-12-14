package ch.fhnw.petra.poodle

import ch.fhnw.petra.poodle.entities.*
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface EventRepository : JpaRepository<Event, Int> {
    fun findByLink(link: String): Optional<Event>
}

interface EventTimeSlotRepository : JpaRepository<EventTimeSlot, Int>
interface ParticipationRepository : JpaRepository<Participation, Int> {
    @Query("SELECT p FROM Participation p JOIN p.event e WHERE e.link = :eventLink")
    fun findByEventLink(@Param("eventLink") eventLink: String): List<Participation>
}
interface VoteRepository : JpaRepository<Vote, Int>
interface MeetingRepository : JpaRepository<Meeting, Int>

//todo: Services