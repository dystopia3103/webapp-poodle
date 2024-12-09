package ch.fhnw.petra.poodle.entities

import jakarta.persistence.*

@Entity
@Table(name = "meeting")
data class Meeting(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,
)