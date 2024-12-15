package ch.fhnw.petra.poodle.entities

import jakarta.persistence.*

@Entity
@Table(name = "participation")
data class Participation(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int = 0,

    @Column(nullable = false, name = "participant_name")
    var participantName: String = "",

    @Column(nullable = false, name = "participant_email")
    var participantEmail: String? = null,

    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "event_id")
    var event: Event? = null,

    @OneToMany(cascade = [CascadeType.ALL])
    var votes: MutableList<Vote> = mutableListOf(),
)