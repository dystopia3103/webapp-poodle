package ch.fhnw.petra.poodle.dtos.viewmodels

data class MeetingViewModel(
    val name: String,
    val description: String,
    val timeSlot: EventTimeSlotViewModel,
    val participants: List<String>,
)
