var timeSlotIndex = 0

function initForm() {
    let jsonString = document.getElementById('time-slots').dataset.timeslots
    if (jsonString) {
        let slotsArr = JSON.parse(jsonString);
        console.log(slotsArr);
    }
    addTimeSlotNode();
}

function addTimeSlotNode() {
    let inputs = `
        <input type="date" name="timeSlots[${timeSlotIndex}].date" placeholder="Pick a date" required>
        <input type="time" name="timeSlots[${timeSlotIndex}].startTime" placeholder="Start time" required>
        <input type="time" name="timeSlots[${timeSlotIndex}].endTime" placeholder="End time" required>
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