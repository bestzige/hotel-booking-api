package dev.bestzige.hotelbooking.exception

import jakarta.persistence.EntityNotFoundException
import lombok.extern.slf4j.Slf4j
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@Slf4j(topic = "GLOBAL_EXCEPTION_HANDLER")
@RestControllerAdvice
class GlobalExceptionHandler : ResponseEntityExceptionHandler() {

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any>? {
        val errorResponse = ErrorResponse(
            HttpStatus.UNPROCESSABLE_ENTITY.value(),
            "Validation error. Check 'errors' field for details."
        )

        for (fieldError in ex.bindingResult.fieldErrors) {
            fieldError.defaultMessage?.let { errorResponse.addValidationError(fieldError.field, it) }
        }

        return ResponseEntity.unprocessableEntity().body(errorResponse)
    }

    @ExceptionHandler(
        RuntimeException::class,
        IllegalArgumentException::class,
        IllegalStateException::class
    )
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleBadRequestException(exception: Exception?, request: WebRequest?): ResponseEntity<Any> {
        return buildErrorResponse(
            exception!!, HttpStatus.BAD_REQUEST, request!!
        )
    }

    @ExceptionHandler(EntityNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleEntityNotFoundException(exception: EntityNotFoundException?, request: WebRequest?): ResponseEntity<Any> {
        return buildErrorResponse(
            exception!!, HttpStatus.NOT_FOUND, request!!
        )
    }

    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleAllUncaughtException(exception: Exception?, request: WebRequest?): ResponseEntity<Any> {
        return buildErrorResponse(
            exception!!, "Unknown error occurred", HttpStatus.INTERNAL_SERVER_ERROR,
            request!!
        )
    }

    private fun buildErrorResponse(
        exception: java.lang.Exception,
        httpStatus: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        return buildErrorResponse(exception, exception.message, httpStatus, request)
    }

    private fun buildErrorResponse(
        exception: java.lang.Exception,
        message: String?,
        httpStatus: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        val errorResponse = message?.let { ErrorResponse(httpStatus.value(), it) }
        return ResponseEntity.status(httpStatus).body(errorResponse)
    }
}