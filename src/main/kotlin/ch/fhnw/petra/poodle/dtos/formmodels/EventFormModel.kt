package ch.fhnw.petra.poodle.dtos.formmodels

import jakarta.validation.Valid
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Pattern

data class EventFormModel(
    @field:NotBlank(message = "Name is required")
    var name: String = "",

    var description: String = "",

    @field:NotEmpty(message = "At least one time slot must be given")
    @field:Valid
    var timeSlots: MutableList<TimeSlotFormModel> = mutableListOf(),

    @field:NotEmpty(message = "At least one participant must be given")
    @field:Valid
    var participantEmails: MutableList<@Email(message = "Each participant must be a valid email address") String> = mutableListOf(),
)

data class TimeSlotFormModel(
    @field:NotBlank(message = "Date is required")
    @field:Pattern(regexp = "\\d{2}\\.\\d{2}\\.\\d{4}", message = "Date must be in the format DD.MM.YYYY")
    var date: String = "",

    @field:Pattern(regexp = "\\d{2}:\\d{2}", message = "Start time must be in the format HH:MM")
    var startTime: String = "",

    @field:Pattern(regexp = "\\d{2}:\\d{2}", message = "End time must be in the format HH:MM")
    var endTime: String = "",
)
