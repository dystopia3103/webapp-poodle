package ch.fhnw.petra.poodle.entities

import jakarta.persistence.*

@Entity
data class Vote(
    @Id
    @GeneratedValue
    private val id: Int? = null,

    @Column(nullable = false)
    @ManyToOne(cascade = [CascadeType.ALL])
    private val participation: Participation,

    @Column(nullable = false)
    @ManyToOne(cascade = [CascadeType.ALL])
    private val timeSlot: EventTimeSlot,
)