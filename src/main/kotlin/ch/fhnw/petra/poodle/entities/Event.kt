package ch.fhnw.petra.poodle.entities

import jakarta.persistence.*

@Entity
data class Event(
    @Id
    @GeneratedValue
    private val id: Int? = null,

    @Column(nullable = false)
    private val link: String,

    @Column(nullable = false)
    private val name: String,

    @Column(nullable = false)
    @OneToOne(cascade = [CascadeType.ALL])
    private val timeSlot: EventTimeSlot,

    @OneToMany(cascade = [CascadeType.ALL])
    private val participations: List<Participation>? = null
)