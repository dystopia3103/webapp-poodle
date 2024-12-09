package ch.fhnw.petra.poodle.entities

import jakarta.persistence.*

@Entity
@Table(name = "vote")
data class Vote(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,

    @JoinColumn
    @ManyToOne(cascade = [CascadeType.ALL])
    val participation: Participation,

    @JoinColumn
    @ManyToOne(cascade = [CascadeType.ALL])
    val timeSlot: EventTimeSlot,
)