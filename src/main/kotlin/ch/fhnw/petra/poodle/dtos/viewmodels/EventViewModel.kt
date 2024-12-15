package ch.fhnw.petra.poodle.dtos.viewmodels

import ch.fhnw.petra.poodle.entities.Event

data class EventViewModel(
    val name: String,
    val link: String,
    val description: String,
    val timeSlots: List<EventTimeSlotViewModel>,
) {
    companion object {
        fun fromEvent(event: Event): EventViewModel {
            return EventViewModel(
                name = event.name,
                link = event.link,
                description = event.description,
                timeSlots = event.timeSlots.map { EventTimeSlotViewModel.fromEventTimeSlot(it) }
            )
        }
    }
}