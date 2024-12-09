package ch.fhnw.petra.poodle.entities

import jakarta.persistence.*

@Entity
@Table(name = "event")
data class Event(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,

    @Column(nullable = false,)
    val link: String,

    @Column(nullable = false)
    val name: String,

    @PrimaryKeyJoinColumn
    @OneToOne(cascade = [CascadeType.ALL])
    val timeSlot: EventTimeSlot,

    @OneToMany(cascade = [CascadeType.ALL])
    val participations: List<Participation>? = null
)