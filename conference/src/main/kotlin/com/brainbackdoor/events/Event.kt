package com.brainbackdoor.events

import kotlin.concurrent.timer

class Event(
    val id: Long,
    val name: String,
    val contents: String,
)

class Events(
    private val events: MutableList<Event>,
) {
    fun pick(id: String): Event {
        val index = id.toCharArray()[0].code % 16

        latencyLogic()
        return events[index]
    }

    private fun latencyLogic() {
        var second: Int = 0
        timer(period = 1000) {
            second++
            if (second == 5) {
                cancel()
            }
        }
    }
}