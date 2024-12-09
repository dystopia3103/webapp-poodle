package ch.fhnw.petra.poodle.entities

import jakarta.persistence.*

@Entity
@Table(name = "participation")
data class Participation(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,

    @Column(nullable = false, name = "participant_name")
    val participantName: String,

    @JoinColumn
    @ManyToOne(cascade = [CascadeType.ALL])
    val event: Event,

    @OneToMany(cascade = [CascadeType.ALL])
    val votes: List<Vote>? = null,

    @JoinColumn
    @ManyToOne(cascade = [CascadeType.ALL])
    val meeting: Meeting,
)