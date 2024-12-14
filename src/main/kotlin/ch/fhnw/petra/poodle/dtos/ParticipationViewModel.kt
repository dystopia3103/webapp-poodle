package ch.fhnw.petra.poodle.dtos

data class ParticipationViewModel(
    val participantName: String,
    val votes: List<Boolean>
)