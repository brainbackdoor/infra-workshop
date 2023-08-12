package com.brainbackdoor.exception

import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import javax.naming.AuthenticationException

private val logger = KotlinLogging.logger { }
@RestControllerAdvice
class ExceptionHandlers {

    @ExceptionHandler(ResourceNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun resourceNotFound(e: ResourceNotFoundException): ResultView {
        logger.error(e.message, e)
        return ResultView.fail(e.message)
    }

    @ExceptionHandler(AuthenticationException::class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    fun invalidInvitationException(e: AuthenticationException): ResultView {
        logger.error(e.message, e)
        return ResultView.fail(e.message)
    }

    @ExceptionHandler(HasNotPermissionException::class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    fun hasNotPermission(e: HasNotPermissionException): ResultView {
        logger.error(e.message, e)
        return ResultView.fail(e.message)
    }

    @ExceptionHandler(IllegalArgumentException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun illegalArgument(e: IllegalArgumentException): ResultView {
        logger.error(e.message, e)
        return ResultView.fail(e.message)
    }

    @ExceptionHandler(RuntimeException::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun invalidInvitationException(e: RuntimeException): ResultView {
        logger.error(e.message, e)
        return ResultView.fail(e.message)
    }
}