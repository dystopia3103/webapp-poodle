package ch.fhnw.petra.poodle.dtos

import ch.fhnw.petra.poodle.entities.Event
import ch.fhnw.petra.poodle.misc.TemporalHelper

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
                timeSlots = event.timeSlots.map {
                    EventTimeSlotViewModel(
                        id = it.id,
                        from = TemporalHelper.dateTimeStringFromInstant(it.start!!),
                        to = TemporalHelper.dateTimeStringFromInstant(it.end!!)
                    )
                }
            )
        }
    }
}