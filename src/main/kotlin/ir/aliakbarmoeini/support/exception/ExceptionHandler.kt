package ir.aliakbarmoeini.support.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionHandler {

    @ExceptionHandler(RequestBodyException::class)
    fun handleException(ex: RequestBodyException) = ResponseEntity(
        (ex.binding.allErrors.joinToString(separator = " | ") { it.defaultMessage!! }),
        HttpStatus.BAD_REQUEST
    )

    @ExceptionHandler(NotFoundException::class)
    fun handleException(ex: NotFoundException) = ResponseEntity(ex.message, HttpStatus.NOT_FOUND)

    @ExceptionHandler(ServiceException::class)
    fun handleException(ex: ServiceException) = ResponseEntity(ex.message, HttpStatus.BAD_REQUEST)

    @ExceptionHandler(AuthenticationException::class)
    fun handleException(ex: AuthenticationException) = ResponseEntity(ex.message, HttpStatus.UNAUTHORIZED)

    @ExceptionHandler(AuthorizationException::class)
    fun handleException(ex: AuthorizationException) = ResponseEntity(ex.message, HttpStatus.UNAUTHORIZED)
}