package ch.fhnw.petra.poodle

import ch.fhnw.petra.poodle.entities.*
import org.springframework.data.jpa.repository.JpaRepository

interface EventRepository : JpaRepository<Event, Int>
interface EventTimeSlotRepository : JpaRepository<EventTimeSlot, Int>
interface ParticipationRepository : JpaRepository<Participation, Int>
interface VoteRepository : JpaRepository<Vote, Int>
interface MeetingRepository : JpaRepository<Meeting, Int>

//todo: Services