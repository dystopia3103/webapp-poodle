var timeSlotIndex = 0;
var participantIndex = 0;

function initForm() {
    initTimeSlots();
    initParticipants();
}

function initTimeSlots() {
    let jsonString = document.getElementById('time-slots').dataset.timeSlots;
    if (jsonString) {
    let slotsArr = JSON.parse(jsonString);
        if (slotsArr && slotsArr.length > 0) {
            slotsArr.forEach(slot => {
                addTimeSlotNode(slot.date, slot.startTime, slot.endTime);
            })
            return;
        }
    }

    addTimeSlotNode();
}

function addTimeSlotNode(date = null, startTime = null, endTime = null) {
    let inputs = `
        <input type="date" name="timeSlots[${timeSlotIndex}].date" value="${date}">
        <input type="time" name="timeSlots[${timeSlotIndex}].startTime" value="${startTime}">
        <input type="time" name="timeSlots[${timeSlotIndex}].endTime" value="${endTime}">
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

function initParticipants() {
    let jsonString = document.getElementById('participant-emails').dataset.participantEmails;
    if (jsonString) {
        let participantsArr = JSON.parse(jsonString);
        if (participantsArr && participantsArr.length > 0) {
            participantsArr.forEach(participant => {
                addParticipantNode(participant);
            })
            return
        }
    }

    addParticipantNode();
}

function addParticipantNode(email = null) {
    let node = document.createElement('input');
    node.name = `participantEmails[${participantIndex}]`;
    node.placeholder = 'example@mail.com';
    node.value = email;
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