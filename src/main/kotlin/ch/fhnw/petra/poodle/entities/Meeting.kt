package ch.fhnw.petra.poodle.entities

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "meeting")
data class Meeting(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int = 0,

    @PrimaryKeyJoinColumn
    @OneToOne(cascade = [CascadeType.ALL])
    var timeSlot: EventTimeSlot? = null,

    @Column(nullable = false)
    val link: String = UUID.randomUUID().toString(),

    @Column(nullable = false)
    var name: String = "",

    @Column(nullable = false)
    var description: String = "",

    @OneToMany(cascade = [CascadeType.ALL])
    val participations: MutableList<Participation> = mutableListOf()
)