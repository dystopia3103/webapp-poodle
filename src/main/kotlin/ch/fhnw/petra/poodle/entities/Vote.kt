package ch.fhnw.petra.poodle.entities

import jakarta.persistence.*

@Entity
@Table(name = "vote")
data class Vote(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int = 0,

    @JoinColumn
    @ManyToOne(cascade = [CascadeType.ALL])
    var participation: Participation? = null,

    @JoinColumn
    @ManyToOne(cascade = [CascadeType.ALL])
    var timeSlot: EventTimeSlot? = null,
)