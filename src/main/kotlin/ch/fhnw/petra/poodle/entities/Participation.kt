package ch.fhnw.petra.poodle.entities

import jakarta.persistence.*

@Entity
data class Participation(
    @Id
    @GeneratedValue
    private val id: Int? = null,

    @Column(nullable = false)
    private val participantName: String,

    @Column(nullable = false)
    @ManyToOne(cascade = [CascadeType.ALL])
    private val event: Event,

    @OneToMany(cascade = [CascadeType.ALL])
    private val votes: List<Vote>? = null,

    @Column(nullable = false)
    @ManyToOne(cascade = [CascadeType.ALL])
    private val meeting: Meeting,
)