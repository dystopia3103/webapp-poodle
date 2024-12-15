package ch.fhnw.petra.poodle.dtos.viewmodels

data class ParticipationViewModel(
    val participantName: String,
    val votes: List<Boolean>
)