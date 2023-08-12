package com.brainbackdoor.conferences.domain

import org.springframework.data.jpa.repository.JpaRepository

interface ApplicantRepository : JpaRepository<Applicant, String>