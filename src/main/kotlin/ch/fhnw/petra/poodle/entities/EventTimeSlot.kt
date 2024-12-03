package ch.fhnw.petra.poodle.entities

import jakarta.persistence.*
import java.time.Instant

data class EventTimeSlot(
    @Id
    @GeneratedValue
    private val id: Int? = null,

    @Column(nullable = false)
    val start: Instant,

    @Column(nullable = false)
    val end: Instant,

    @Column(nullable = false)
    @ManyToOne(cascade = [CascadeType.ALL])
    private val event: Event
)
