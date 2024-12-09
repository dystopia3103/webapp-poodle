package ch.fhnw.petra.poodle.entities

import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "event_time_slot")
data class EventTimeSlot(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,

    @Column(nullable = false, name = "start_time")
    val start: Instant,

    @Column(nullable = false, name = "end_time")
    val end: Instant,

    @JoinColumn
    @ManyToOne(cascade = [CascadeType.ALL])
    val event: Event? = null
)
