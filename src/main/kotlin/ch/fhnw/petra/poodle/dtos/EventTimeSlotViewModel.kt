package ch.fhnw.petra.poodle.dtos

import ch.fhnw.petra.poodle.entities.EventTimeSlot
import ch.fhnw.petra.poodle.misc.TemporalHelper

data class EventTimeSlotViewModel(
    val id: Int,
    val from: String,
    val to: String
) {
    companion object {
        fun fromEventTimeSlot(eventTimeSlot: EventTimeSlot): EventTimeSlotViewModel {
            return EventTimeSlotViewModel(
                id = eventTimeSlot.id,
                from = TemporalHelper.dateTimeStringFromInstant(eventTimeSlot.start!!),
                to = TemporalHelper.dateTimeStringFromInstant(eventTimeSlot.end!!)
            )
        }
    }
}
