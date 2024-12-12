package ch.fhnw.petra.poodle.dtos

data class EventFormModel(
    var name: String = "",
    var description: String = "",
    var timeSlots: MutableList<TimeSlotFormModel> = mutableListOf(),
)

data class TimeSlotFormModel(
    var date: String = "",
    var startTime: String = "",
    var endTime: String = "",
)
