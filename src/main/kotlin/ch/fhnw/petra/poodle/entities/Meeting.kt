package ch.fhnw.petra.poodle.entities

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "meeting")
data class Meeting(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int = 0,

    @OneToOne(cascade = [CascadeType.ALL])
    var timeSlot: EventTimeSlot? = null,

    @OneToOne(cascade = [CascadeType.ALL])
    val event: Event? = null
)