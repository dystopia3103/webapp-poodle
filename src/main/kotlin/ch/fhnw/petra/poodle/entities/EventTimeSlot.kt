package ch.fhnw.petra.poodle.entities

import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "event_time_slot")
data class EventTimeSlot(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int = 0,

    @Column(nullable = false, name = "start_time")
    var start: Instant? = null,

    @Column(nullable = false, name = "end_time")
    var end: Instant? = null,

    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "event_id")
    var event: Event? = null
)
