package com.brainbackdoor.auth

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class AdminAuth(
    val validAdmin: Boolean = true,
)