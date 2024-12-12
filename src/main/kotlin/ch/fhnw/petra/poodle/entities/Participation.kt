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

    @JoinColumn
    @ManyToOne(cascade = [CascadeType.ALL])
    var event: Event? = null,

    @OneToMany(cascade = [CascadeType.ALL])
    var votes: MutableList<Vote> = mutableListOf(),

    @JoinColumn
    @ManyToOne(cascade = [CascadeType.ALL])
    var meeting: Meeting? = null,
)