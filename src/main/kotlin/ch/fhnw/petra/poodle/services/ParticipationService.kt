package ch.fhnw.petra.poodle.services

import ch.fhnw.petra.poodle.ParticipationRepository
import ch.fhnw.petra.poodle.entities.Participation
import org.springframework.stereotype.Service

@Service
class ParticipationService(private val participationRepo: ParticipationRepository) {

    fun findByEventLink(link: String): List<Participation> {
        return participationRepo.findByEventLink(link)
    }

    fun save(participation: Participation) {
        participationRepo.save(participation)
    }

}