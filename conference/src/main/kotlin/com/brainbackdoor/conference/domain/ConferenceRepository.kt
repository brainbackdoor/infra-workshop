package com.brainbackdoor.conference.domain

import org.springframework.data.jpa.repository.JpaRepository

interface ConferenceRepository : JpaRepository<Conference, String>