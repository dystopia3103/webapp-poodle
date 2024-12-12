package ch.fhnw.petra.poodle.entities

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "event")
data class Event(
    @Id
    @GeneratedValue
    var id: Int = 0,

    @Column(nullable = false, unique = true)
    val link: String = UUID.randomUUID().toString(),

    @Column(nullable = false)
    var name: String = "",

    @Column(nullable = false)
    var description: String = "",

    @ElementCollection
    var participantEmails: MutableList<String> = mutableListOf(),

    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    var timeSlots: MutableList<EventTimeSlot> = mutableListOf(),

    @OneToMany(cascade = [CascadeType.ALL])
    var participations: MutableList<Participation> = mutableListOf(),

    )