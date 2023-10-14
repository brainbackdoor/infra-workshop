package com.brainbackdoor.events

import com.brainbackdoor.support.delay

class Event(
    val id: Long,
    val name: String,
    val contents: String,
)

class Events(
    private val events: MutableList<Event>,
) {
    fun pick(id: Int): Event {
        latencyLogic()
        return events[id]
    }

    private fun latencyLogic() {
        delay(5000L)
    }
}