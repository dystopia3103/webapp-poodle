var timeSlotIndex = 0;
var participantIndex = 0;

function initForm() {
    let jsonString = document.getElementById('time-slots').dataset.timeslots
    if (jsonString) {
        let slotsArr = JSON.parse(jsonString);
        console.log(slotsArr);
    }

    // if no previous existing data
    addTimeSlotNode();
    addParticipantNode();
}

function addTimeSlotNode() {
    let inputs = `
        <input type="date" name="timeSlots[${timeSlotIndex}].date" placeholder="Pick a date">
        <input type="time" name="timeSlots[${timeSlotIndex}].startTime" placeholder="Start time">
        <input type="time" name="timeSlots[${timeSlotIndex}].endTime" placeholder="End time">
    `;
    let node = document.createElement('div')
    node.id = `time-slot-${timeSlotIndex}`;
    node.innerHTML = inputs;

    document.getElementById('time-slots').appendChild(node)
    timeSlotIndex++;
}

function removeTimeSlotNode() {
    document.getElementById('time-slots').lastElementChild.remove();
    timeSlotIndex--;
}

function addParticipantNode() {
    let node = document.createElement('input');
    node.name = `participantEmails[${participantIndex}]`;
    node.placeholder = 'Participant email';
    document.getElementById('participant-emails').appendChild(node)
    participantIndex++;
}

function removeParticipantNode() {
    document.getElementById('participant-emails').lastElementChild.remove();
    participantIndex--;
}

document.addEventListener('DOMContentLoaded', function () {
    initForm();
});