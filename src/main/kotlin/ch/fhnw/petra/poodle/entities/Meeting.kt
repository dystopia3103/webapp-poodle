package ch.fhnw.petra.poodle.entities

import jakarta.persistence.*

@Entity
data class Meeting(
    @Id
    @GeneratedValue
    private val id: Int? = null,


)
