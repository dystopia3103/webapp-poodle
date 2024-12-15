package ch.fhnw.petra.poodle.dtos.formmodels

import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty

data class ParticipationFormModel(
    @field:NotBlank(message = "Event is required")
    var eventLink: String = "",

    @field:NotBlank(message = "Name is required")
    var participantName: String = "",

    @field:NotEmpty(message = "At least one time slot must be given")
    @Valid
    var participations: MutableList<TimeSlotParticipationFormModel> = mutableListOf()
)

data class TimeSlotParticipationFormModel(
    var timeSlotId: Int = 0,
)