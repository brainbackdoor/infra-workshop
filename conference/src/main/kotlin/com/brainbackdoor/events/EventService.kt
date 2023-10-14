package com.brainbackdoor.events

import com.brainbackdoor.events.EventRepository.Companion.events
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
class EventService {

    // @Cacheable(cacheNames = ["mbtiCache"], key = "#id")
    fun findMbtiBy(id: Int) = events().pick(id)
}