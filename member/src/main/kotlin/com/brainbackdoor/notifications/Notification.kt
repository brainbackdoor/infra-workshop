package com.brainbackdoor.notifications

interface Notification {
    fun send(recipient: String, subject: String, contents: String)
    fun send(recipient: List<String>, subject: String, contents: String)
}